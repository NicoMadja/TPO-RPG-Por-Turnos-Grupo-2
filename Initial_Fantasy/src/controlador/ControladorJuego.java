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
        // 1. Configurar la Ventana Principal
        ventanaPrincipal = new JFrame("Initial Fantasy - Aventura RPG");
        ventanaPrincipal.setSize(1024, 768);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLocationRelativeTo(null);


        // 2. Inicializar los paneles
        panelMenu = new PantallaMenu();
        panelSeleccion = new PantallaSeleccionPersonaje();
        panelMapa = new PantallaMapa();
        panelTienda = new PantallaTienda();

        repositorio = new RepositorioPartida("partida_guardada.sav");

        // 2. Verificar si hay partida para habilitar el botón "Cargar Partida"
        if (!repositorio.existePartida()) {
            panelMenu.getBtnCargarPartida().setEnabled(false);
        }

        // 3. EVENTO: Clic en "Nueva Partida"
        panelMenu.getBtnNuevaPartida().addActionListener(e -> {
            // Si ya hay una partida guardada, preguntamos antes de sobrescribir
            if (repositorio.existePartida()) {
                int respuesta = JOptionPane.showConfirmDialog(
                        ventanaPrincipal,
                        "Ya existe una partida guardada. Al iniciar una nueva, se borrará. ¿Deseas continuar?",
                        "Advertencia",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    repositorio.borrarPartida();
                    panelMenu.getBtnCargarPartida().setEnabled(false);
                } else {
                    return; // Cancela la acción de nueva partida
                }
            }

            // A) Instancias tu modelo (la lógica real del juego)
            this.heroesDelModelo = Arrays.asList(
                    new Guerrero("Guerrero"),
                    new Arquero("Arquero"),
                    new Tanque("Tanque"),
                    new Curandero("Curandero"),
                    new Mago("Mago")
            );

            // B) Traduces el Modelo a DTOs
            List<PersonajeDTO> disponiblesDTO = new ArrayList<>();

            for (Personaje p : heroesDelModelo) {
                disponiblesDTO.add(new PersonajeDTO(
                        p.getNombre(),
                        p.getNivel(),
                        p.getVidaMaxima(),
                        p.getVidaActual(),
                        p.getManaMaximo(),
                        p.getManaActual(),
                        p.getAtaqueBase(),
                        p.getAtaqueActual(),
                        p.getDefensaBase(),
                        p.getDefensaActual(),
                        p.isDefendiendo(),
                        p.getVelocidadBase(),
                        p.getVelocidadActual(),
                        p.getRutaSprite(),
                        p.getExperienciaActual(),
                        p.getExperienciaRequerida()
                ));
            }

            // C) Le pasas los DTOs a la vista
            panelSeleccion.cargarPersonajes(disponiblesDTO);
            cambiarPantalla(panelSeleccion);
        });

        // 4. EVENTO: Clic en "Continuar"
        panelSeleccion.getBtnContinuar().addActionListener(e -> {
            // A) La vista te devuelve DTOs
            partyJugador = new Party();
            System.out.println(panelSeleccion.getPersonajesSeleccionados());
            List<PersonajeDTO> elegidosDTO = panelSeleccion.getPersonajesSeleccionados();
            // Reconstruimos la Party con los objetos del modelo comparando nombres
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
            // Generamos el Prefab y asignamos el Controlador de Mapa
            MapaPrefab prefab = gestorDeJuego.elegirMapaAlAzar();
            Mapa mapaLogico = prefab.crear(partyJugador);


            // Le pasamos "this" para que atrape los eventos (Batalla, Tienda)
            controladorMapa = new ControladorMapa(mapaLogico, prefab.getCoordenadasVisuales(), panelMapa, this, partyJugador);

            cambiarPantalla(panelMapa);

            // B) (Próximo paso) Aquí armarás tu Party y pasarás al Mapa
        });

        // 5. Arrancar mostrando el Menú
        cambiarPantalla(panelMenu);
        ventanaPrincipal.setVisible(true);

        panelMenu.getBtnCargarPartida().addActionListener(e -> {
            try {
                // 1. Cargar los datos guardados
                PartidaDTO estadoGuardado = repositorio.cargar();

                List<PersonajeDTO> heroesCargados =
                        estadoGuardado.getHeroesGuardados();

                // 2. Reconstruir la party con sus estadísticas originales
                partyJugador = new Party();

                for (PersonajeDTO dto : heroesCargados) {
                    Personaje personaje = reconstruirPersonaje(dto);
                    partyJugador.agregarPersonaje(personaje);
                }
                if (estadoGuardado.getInventarioGuardado() != null) {
                    partyJugador.setInventario(estadoGuardado.getInventarioGuardado());
                }
                // 3. Reconstruir el mapa
                FabricaMapas fabricaMapas = new FabricaMapas();

                MapaPrefab prefab = fabricaMapas.crearPrefab(
                        estadoGuardado.getIdMapa()
                );

                Mapa mapaLogico = prefab.crear(partyJugador);

                mapaLogico.restaurarNodoActual(
                        estadoGuardado.getIdNodoActual()
                );

                controladorMapa = new ControladorMapa(
                        mapaLogico,
                        prefab.getCoordenadasVisuales(),
                        panelMapa,
                        this,
                        partyJugador
                );

                cambiarPantalla(panelMapa);

                // 4. Restaurar la posición guardada
                mapaLogico.restaurarNodoActual(
                        estadoGuardado.getIdNodoActual()
                );

                // 5. Conectar el controlador con el mapa restaurado
                controladorMapa = new ControladorMapa(
                        mapaLogico,
                        prefab.getCoordenadasVisuales(),
                        panelMapa,
                        this,
                        partyJugador
                );

                // 6. Mostrar la partida
                cambiarPantalla(panelMapa);

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "¡Partida cargada con éxito!",
                        "Cargar partida",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (PartidaNoEncontradaException ex) {
                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "No se encontró el archivo de guardado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (Exception ex) {
                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        ventanaPrincipal,
                        "Error al cargar la partida: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    private Personaje reconstruirPersonaje(PersonajeDTO dto) {
        // 1. Usamos la fábrica para crear la instancia base limpia (Mago, Guerrero, etc.)
        Personaje personajeBase = FabricaPersonajes.crearPersonaje(dto.getNombre());

        // Si no se pudo crear, retornamos null
        if (personajeBase == null) {
            return null;
        }

        // 2. Restauramos todas las estadísticas desde el DTO usando tu método
        personajeBase.restaurarEstado(
                dto.getNivel(),
                dto.getVidaMaxima(),
                dto.getVidaActual(),
                dto.getManaMaximo(),
                dto.getManaActual(),
                dto.getAtaqueBase(),
                dto.getAtaqueActual(),
                dto.getDefensaBase(),
                dto.getDefensaActual(),
                dto.isDefendiendo(),
                dto.getVelocidadBase(),
                dto.getVelocidadActual()
        );

        // 3. Restauramos la experiencia usando tu método
        personajeBase.restaurarExperiencia(
                dto.getExperienciaActual(),
                dto.getExperienciaRequerida()
        );

        return personajeBase;
    }


    @Override
    public void alEntrarEnBatalla(Batalla batallaLogica) {
        System.out.println("¡Transición del Mapa a Batalla!");

        // Inicializamos la vista y el controlador de la batalla actual
        panelBatalla = new PantallaBatalla();
        Runnable volverAlMapa = () -> cambiarPantalla(panelMapa);
        Runnable reiniciarJuego = () -> {
            partyJugador = null; // Borramos la memoria de la partida actual
            controladorMapa = null;
            controladorBatalla = null;// Destruimos el mapa
            // B. Instanciamos de cero las pantallas por si quedaron sucias (Opcional pero recomendado)
            panelMapa = new PantallaMapa();


            // C. Volvemos al menú principal
            cambiarPantalla(panelMenu);

            // D. Cerramos cualquier JDialog modal que haya quedado flotando (El Game Over)
            Window[] ventanasActivas = Window.getWindows();
            for (Window w : ventanasActivas) {
                if (w instanceof JDialog) {
                    w.dispose();
                }
            }// Volvemos a la pantalla de inicio
        };
        controladorBatalla = new ControladorBatalla(batallaLogica, panelBatalla, volverAlMapa,reiniciarJuego);
        panelBatalla.setControlador(controladorBatalla);
        cambiarPantalla(panelBatalla);
        controladorBatalla.iniciar();
        };




    @Override
    public void alEntrarEnTienda(Tienda tienda) {
        System.out.println("¡Transición del Mapa a Tienda!");

        // Pasamos un Runnable para que al hacer clic en "Continuar Viaje", vuelva al mapa
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
            // Esta lógica se ejecuta al presionar "Descansar"

            // Si el metodo está en Party (Recomendado por GRASP)
            campamento.descansar();

            // O si lo dejaste en la clase Campamento:
            // campamento.descansar();

            System.out.println("La Party ha recuperado el 100% de HP y MP.");
        });

        // Muestra el modal sobre el mapa
        modal.setVisible(true);
    }

    @Override
    public void alGanarJuego(){
        System.out.println("¡Jefe Final derrotado! Mostrando pantalla de victoria.");
        //cambiarPantalla(panelVictoria);
    }

    @Override
    public void alVolverAlMenu() {
        System.out.println("Saliendo de la partida y volviendo al menú...");

        // Limpiamos los controladores y datos temporales para que no haya ghosting
        partyJugador = null;
        controladorMapa = null;
        controladorBatalla = null;

        // Instanciamos el mapa de cero por si quedó sucio
        panelMapa = new PantallaMapa();

        // Cambiamos a la pantalla de inicio
        cambiarPantalla(panelMenu);

        // Opcional: Refrescar el botón de Cargar Partida si se creó un guardado nuevo
        if (repositorio.existePartida()) {
            panelMenu.getBtnCargarPartida().setEnabled(true);
        }
    }


    // Metodo para intercambiar los paneles sin abrir ventanas nuevas (el que faltaba)
    private void cambiarPantalla(JPanel nuevaPantalla) {
        ventanaPrincipal.setContentPane(nuevaPantalla);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }

}