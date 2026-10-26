package controlador;

import dto.PersonajeDTO;
import modelo.batalla.Batalla;
import modelo.campamento.Campamento;
import modelo.entidades.personajes.*;
import modelo.mundo.EscenarioDeNodos;
import modelo.mundo.mapa.Mapa;
import modelo.mundo.mapa.prefabs.Mapa1;
import modelo.party.Party;
import modelo.tienda.Tienda;
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
    // --- ESTADO GLOBAL ---
    private Party partyJugador;
    private List<Personaje> heroesDelModelo;

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

        // 3. EVENTO: Clic en "Nueva Partida"
        panelMenu.getBtnNuevaPartida().addActionListener(e -> {

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

            System.out.println("Party armada. Generando Mapa1...");
            System.out.println("Héroes enviados al mapa: " + partyJugador.getPersonajes().size());
            // Generamos el Prefab y asignamos el Controlador de Mapa
            Mapa1 prefab = new Mapa1();
            Mapa mapaLogico = prefab.crear(partyJugador);


            // Le pasamos "this" para que atrape los eventos (Batalla, Tienda)
            controladorMapa = new ControladorMapa(mapaLogico, prefab.getCoordenadasVisuales(), panelMapa, this);

            cambiarPantalla(panelMapa);

            // B) (Próximo paso) Aquí armarás tu Party y pasarás al Mapa
        });

        // 5. Arrancar mostrando el Menú
        cambiarPantalla(panelMenu);
        ventanaPrincipal.setVisible(true);
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

            // Si el método está en Party (Recomendado por GRASP)
            campamento.descansar();

            // O si lo dejaste en la clase Campamento:
            // campamento.descansar();

            System.out.println("La Party ha recuperado el 100% de HP y MP.");
        });

        // Muestra el modal sobre el mapa
        modal.setVisible(true);
    }

    // Método para intercambiar los paneles sin abrir ventanas nuevas (el que faltaba)
    private void cambiarPantalla(JPanel nuevaPantalla) {
        ventanaPrincipal.setContentPane(nuevaPantalla);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }

}