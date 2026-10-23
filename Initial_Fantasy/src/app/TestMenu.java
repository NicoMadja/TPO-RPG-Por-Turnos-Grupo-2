package app;

import controlador.ControladorMenu;
import javax.swing.SwingUtilities;

public class TestMenu {
    public static void main(String[] args) {
        // SwingUtilities asegura que la interfaz gráfica se ejecute correctamente
        SwingUtilities.invokeLater(() -> {

            System.out.println("Iniciando el juego...");

            // Al instanciar el controlador, mágicamente se crea la ventana
            // y se muestra el PantallaMenu inicial.
            new ControladorMenu();

        });
    }
}