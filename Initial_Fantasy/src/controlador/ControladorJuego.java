package controlador;

import dto.PartidaDTO;
import dto.PersonajeDTO;
import excepciones.PartidaNoEncontradaException;
import modelo.batalla.Batalla;
import modelo.campamento.Campamento;
import modelo.entidades.personajes.*;
import modelo.gestores.GestorDeJuego;
import modelo.mundo.EscenarioDeNodos;
import modelo.mundo.mapa.FabricaMapas;
import modelo.mundo.mapa.Mapa;
import modelo.mundo.mapa.MapaPrefab;
import modelo.mundo.mapa.prefabs.Mapa1;
import modelo.mundo.mapa.prefabs.Mapa2;
import modelo.party.Party;
import modelo.tienda.Tienda;
import repositorioPartida.RepositorioPartida;
import vista.pantallas.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorJuego implements EscenarioDeNodos {
    private JFrame ventanaPrincipal;

    // Vistas
    private PantallaMenu panelMenu;
    private PantallaSeleccionPersonaje panelSeleccion;
    private PantallaMapa panelMapa;
    private PantallaBatalla panelBatalla;
    private PantallaTienda panelTienda;
    private ControladorMapa controladorMapa;
    private ControladorBatalla controladorBatalla;
    private RepositorioPartida repositorio;
    private Party partyJugador;
    private List<Personaje> heroesDelModelo;

    private final GestorDeJuego gestorDeJuego = new GestorDeJuego();
    public ControladorJuego() {
        // ventana principal
        ventanaPrincipal = new JFrame("Initial Fantasy - Aventura RPG");
        ventanaPrincipal.setSize(1024, 768);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLocationRelativeTo(null);

        // inicializar paneles
        panelMenu = new PantallaMenu();
        panelSeleccion = new PantallaSeleccionPersonaje();
        panelMapa = new PantallaMapa();
        panelTienda = new PantallaTienda();

        repositorio = new RepositorioPartida("partida_guardada.sav");

        // verificar si hay partida para habilitar el botón "Cargar Partida"
        if (!repositorio.existePartida()) {
            panelMenu.getBtnCargarPartida().setEnabled(false);
        }

        // clic en "Nueva Partida"
        panelMenu.getBtnNuevaPartida().addActionListener(e -> {
            // Si ya hay una partida guardada, preguntamos antes de sobrescribir
            if (repositorio.existePartida()) {
                int respuesta = JOptionPane.showConfirmDialog(
                        ventanaPrincipal,
                        "Ya existe una partida guardada. Se reemplazará recién cuando guardes la nueva. ¿Deseas continuar?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (respuesta != JOptionPane.YES_OPTION) {
                    return; // Cancela la acción de nueva partida
                }
            }

            // instanciamos
            this.heroesDelModelo = Arrays.asList(
                    new Guerrero("Guerrero"),
                    new Arquero("Arquero"),
                    new Tanque("Tanque"),
                    new Curandero("Curandero"),
                    new Mago("Mago")
            );

            // pasamos a DTOs
            List<PersonajeDTO> disponiblesDTO = new ArrayList<>();
            for (Personaje p : heroesDelModelo) {
                disponiblesDTO.add(new PersonajeDTO(p.getNombre(), p.getNivel(), p.getVidaMaxima(), p.getVidaActual(),
                        p.getManaMaximo(), p.getManaActual(), p.getAtaqueBase(), p.getAtaqueActual(), p.getDefensaBase(),
                        p.getDefensaActual(), p.isDefendiendo(), p.getVelocidadBase(), p.getVelocidadActual(),
                        p.getRutaSprite(), p.getExperienciaActual(), p.getExperienciaRequerida()));
            }

            // pasamos los DTOs a la vista
            panelSeleccion.cargarPersonajes(disponiblesDTO);
            cambiarPantalla(panelSeleccion);
        });

        // clic en "Continuar"
        panelSeleccion.getBtnContinuar().addActionListener(e -> {
            // la vista te devuelve DTOs
            partyJugador = new Party();
            System.out.println(panelSeleccion.getPersonajesSeleccionados());
            List<PersonajeDTO> elegidosDTO = panelSeleccion.getPersonajesSeleccionados();
            // reconstruimos la Party con los objetos del modelo comparando nombres
            for (PersonajeDTO elegidos: elegidosDTO) {
                for (Personaje real : this.heroesDelModelo) {
                    if (real.getNombre().equals(elegidos.getNombre())) {
                        partyJugador.agregarPersonaje(real);
                        break;
                    }
                }
            }

            System.out.println("Party armada. Generando Mapa2...");
            System.out.println("Héroes enviados al mapa: " + partyJugador.getPersonajes().size());
            // generamos el Prefab y asignamos el Controlador de Mapa
            MapaPrefab prefab = gestorDeJuego.elegirMapaAlAzar();
            Mapa mapaLogico = prefab.crear(partyJugador);

            controladorMapa = new ControladorMapa(mapaLogico, prefab.getCoordenadasVisuales(), panelMapa, this, partyJugador);

            cambiarPantalla(panelMapa);
        });

        // arrancar mostrando el Menú
        cambiarPantalla(panelMenu);
        ventanaPrincipal.setVisible(true);

        panelMenu.getBtnCargarPartida().addActionListener(e -> {
            try {
                // 1. cargar los datos guardados
                PartidaDTO estadoGuardado = repositorio.cargar();
                List<PersonajeDTO> heroesCargados =
                        estadoGuardado.getHeroesGuardados();

                // 2. reconstruir la party con sus estadísticas originales
                partyJugador = new Party();

                for (PersonajeDTO dto : heroesCargados) {
                    Personaje personaje = reconstruirPersonaje(dto);
                    partyJugador.agregarPersonaje(personaje);
                }
                if (estadoGuardado.getInventarioGuardado() != null) {
                    partyJugador.setInventario(estadoGuardado.getInventarioGuardado());
                }
                partyJugador.setMonedero(estadoGuardado.getMonederoGuardado());

                // 3. reconstruir el mapa
                FabricaMapas fabricaMapas = new FabricaMapas();
                MapaPrefab prefab = fabricaMapas.crearPrefab(
                        estadoGuardado.getIdMapa()
                );
                Mapa mapaLogico = prefab.crear(partyJugador);

                // 4. restaurar la posición guardada
                mapaLogico.restaurarNodoActual(
                        estadoGuardado.getIdNodoActual()
                );

                // 5. crear el controlador una sola vez
                controladorMapa = new ControladorMapa(mapaLogico, prefab.getCoordenadasVisuales(), panelMapa, this, partyJugador);

                // 6. mostrar la partida
                cambiarPantalla(panelMapa);
                JOptionPane.showMessageDialog(ventanaPrincipal, "¡Partida cargada con éxito!", "Cargar partida", JOptionPane.INFORMATION_MESSAGE);

            } catch (PartidaNoEncontradaException ex) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "No se encontró el archivo de guardado.", "Error", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ventanaPrincipal, "Error al cargar la partida: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private Personaje reconstruirPersonaje(PersonajeDTO dto) {
        // 1. usamos la fábrica para crear la instancia base limpia (Mago, Guerrero, etc.)
        Personaje personajeBase = FabricaPersonajes.crearPersonaje(dto.getNombre());
        // si no se puede, return null
        if (personajeBase == null) {
            return null;
        }

        // 2. restauramos todas las estadísticas desde el DTO usando tu método
        personajeBase.restaurarEstado(dto.getNivel(), dto.getVidaMaxima(), dto.getVidaActual(), dto.getManaMaximo(),
                dto.getManaActual(), dto.getAtaqueBase(), dto.getAtaqueActual(), dto.getDefensaBase(), dto.getDefensaActual(),
                dto.isDefendiendo(), dto.getVelocidadBase(), dto.getVelocidadActual());

        // 3. restauramos la experiencia
        personajeBase.restaurarExperiencia(dto.getExperienciaActual(), dto.getExperienciaRequerida()
        );

        return personajeBase;
    }

    private void mostrarPantallaVictoria() {
        PantallaVictoria pantallaVictoria = new PantallaVictoria();
        pantallaVictoria.configurarVictoria(() -> alVolverAlMenu());
        cambiarPantalla(pantallaVictoria);
    }


    @Override
    public void alEntrarEnBatalla(Batalla batallaLogica) {
        System.out.println("¡Transición del Mapa a Batalla!");

        // inicializamos la vista y el controlador de la batalla actual
        panelBatalla = new PantallaBatalla();
        Runnable volverAlMapa = () -> cambiarPantalla(panelMapa);
        Runnable reiniciarJuego = () -> {
            partyJugador = null; // borramos la memoria de la partida actual
            controladorMapa = null;
            controladorBatalla = null;// destruimos el mapa
            // instanciamos de cero las pantallas por si quedaron sucias
            panelMapa = new PantallaMapa();

            // volvemos al menú principal
            cambiarPantalla(panelMenu);

            // cerramos cualquier JDialog modal que haya quedado flotando (el Game Over)
            Window[] ventanasActivas = Window.getWindows();
            for (Window w : ventanasActivas) {
                if (w instanceof JDialog) {
                    w.dispose();
                }
            }
        };

        controladorBatalla = new ControladorBatalla(batallaLogica, panelBatalla, volverAlMapa,reiniciarJuego, this::alGanarJuego);
        panelBatalla.setControlador(controladorBatalla);
        cambiarPantalla(panelBatalla);
        controladorBatalla.iniciar();
        }


    @Override
    public void alEntrarEnTienda(Tienda tienda) {
        System.out.println("¡Transición del Mapa a Tienda!");

        // pasamos un Runnable para que al hacer clic en "Continuar Viaje", vuelva al mapa
        Runnable volverAlMapa = () -> cambiarPantalla(panelMapa);
        ControladorTienda ctrlTienda = new ControladorTienda(tienda, panelTienda, volverAlMapa);
        panelTienda.setControlador(ctrlTienda);
        ctrlTienda.iniciar();
        cambiarPantalla(panelTienda);
    }

    @Override
    public void alEntrarEnCampamento(Campamento campamento) {
        System.out.println("Descansando en el Campamento...");
        PantallaCampamento modal = new PantallaCampamento(ventanaPrincipal, () -> {
            campamento.descansar();
            System.out.println("La Party ha recuperado el 100% de HP y MP.");
        });
        modal.setVisible(true);
    }

    @Override
    public void alGanarJuego(){
        System.out.println(
                "¡Jefe final derrotado! Mostrando pantalla de victoria."
        );
        mostrarPantallaVictoria();
    }

    @Override
    public void alVolverAlMenu() {
        System.out.println("Saliendo de la partida y volviendo al menú...");
        // limpiamos los controladores y datos temporales
        partyJugador = null;
        controladorMapa = null;
        controladorBatalla = null;

        panelMapa = new PantallaMapa();

        // cambiamos a la pantalla de inicio
        cambiarPantalla(panelMenu);

        // refrescamos el botón de Cargar Partida si se creó un guardado nuevo
        if (repositorio.existePartida()) {
            panelMenu.getBtnCargarPartida().setEnabled(true);
        }
    }

    // Metodo para intercambiar los paneles sin abrir ventanas nuevas
    private void cambiarPantalla(JPanel nuevaPantalla) {
        ventanaPrincipal.setContentPane(nuevaPantalla);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }
}
