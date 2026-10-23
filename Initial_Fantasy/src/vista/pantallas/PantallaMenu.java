package vista.pantallas;

import java.awt.*;
import javax.swing.*;
import utils.CargadorImagenes;

public class PantallaMenu extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton btnNuevaPartida;
    private JButton btnCargarPartida;
    private JButton btnSalir;

    private Image imagenFondo;

    public PantallaMenu() {
        // Layout nulo para posicionamiento absoluto de los botones
        setLayout(null);

        // 1. Cargar la imagen de fondo
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/menuFondo.jpg");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        }

        // 2. Crear los botones con el estilo redondeado/blanco
        btnNuevaPartida = crearBotonMenu("Nueva Partida");
        btnCargarPartida = crearBotonMenu("Cargar Partida");
        btnSalir = crearBotonMenu("Salir");

        // Acción por defecto del botón Salir
        btnSalir.addActionListener(e -> System.exit(0));

        // 3. Posicionar los botones manualmente (X, Y, Ancho, Alto)
        // Basado en el ancho de 1024 que definimos en ControladorJuego
        int ventanaAncho = 1024;
        int botonAncho = 240;
        int botonAlto = 50;
        int posicionX = (ventanaAncho - botonAncho) / 2; // Centrado horizontal

        btnNuevaPartida.setBounds(posicionX, 320, botonAncho, botonAlto);
        btnCargarPartida.setBounds(posicionX, 390, botonAncho, botonAlto);
        btnSalir.setBounds(posicionX, 460, botonAncho, botonAlto);

        // 4. Agregar los botones directamente a este panel
        add(btnNuevaPartida);
        add(btnCargarPartida);
        add(btnSalir);
    }

    // Dibujamos la imagen de fondo directamente sobre el panel del menú
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fondo de respaldo celeste claro por si falla la imagen
            g.setColor(new Color(173, 216, 230));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Método auxiliar para darle estilo a los botones
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.WHITE);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    // --- GETTERS ---
    public JButton getBtnNuevaPartida() { return btnNuevaPartida; }
    public JButton getBtnCargarPartida() { return btnCargarPartida; }
}