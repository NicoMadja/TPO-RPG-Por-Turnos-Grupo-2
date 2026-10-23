package controlador;

import dto.PersonajeDTO;
import modelo.entidades.personajes.*;
import vista.pantallas.PantallaMenu;
import vista.pantallas.PantallaSeleccionPersonaje;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorMenu {
    private JFrame ventanaPrincipal;

    // Vistas
    private PantallaMenu panelMenu;
    private PantallaSeleccionPersonaje panelSeleccion;

    public ControladorMenu() {
        // 1. Configurar la Ventana Principal
        ventanaPrincipal = new JFrame("Mi RPG");
        ventanaPrincipal.setSize(1024, 768);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLocationRelativeTo(null);

        // 2. Inicializar los paneles
        panelMenu = new PantallaMenu();
        panelSeleccion = new PantallaSeleccionPersonaje();

        // 3. EVENTO: Clic en "Nueva Partida"
        panelMenu.getBtnNuevaPartida().addActionListener(e -> {

            // A) Instancias tu modelo (la lógica real del juego)
            List<Personaje> heroesDelModelo = Arrays.asList(
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
                        "", // <- Texto vacío temporal para el sprite y evitar el error
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
            List<PersonajeDTO> elegidosDTO = panelSeleccion.getPersonajesSeleccionados();
            System.out.println("Los 3 héroes elegidos (DTOs) son: " + elegidosDTO);

            // B) (Próximo paso) Aquí armarás tu Party y pasarás al Mapa
        });

        // 5. Arrancar mostrando el Menú
        cambiarPantalla(panelMenu);
        ventanaPrincipal.setVisible(true);
    }

    // Método para intercambiar los paneles sin abrir ventanas nuevas (el que faltaba)
    private void cambiarPantalla(JPanel nuevaPantalla) {
        ventanaPrincipal.setContentPane(nuevaPantalla);
        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }

    // Método principal para ejecutar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControladorMenu());
    }
}