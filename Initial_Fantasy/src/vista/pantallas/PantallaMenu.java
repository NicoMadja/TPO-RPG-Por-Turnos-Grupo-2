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
        // centramos automáticamente los componentes en la pantalla
        setLayout(new GridBagLayout());

        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/menu_fondo.jpg");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        }

        // creamos los botones
        btnNuevaPartida = crearBotonMenu("Nueva Partida");
        btnCargarPartida = crearBotonMenu("Cargar Partida");
        btnSalir = crearBotonMenu("Salir");
        btnSalir.addActionListener(e -> System.exit(0));

        // creamos un contenedor transparente para apilar los botones verticalmente
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setOpaque(false);

        // agregamos los botones al contenedor con separadores
        panelBotones.add(btnNuevaPartida);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(btnCargarPartida);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(btnSalir);

        // agregamos el grupo de botones al panel principal
        add(panelBotones);
    }

    // dibujamos la imagen de fondo directamente sobre el panel del menú
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // fondo de respaldo celeste claro por si falla la imagen
            g.setColor(new Color(173, 216, 230));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // diseño de botones
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.WHITE);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Dimension dimensionBoton = new Dimension(240, 50);
        boton.setPreferredSize(dimensionBoton);
        boton.setMaximumSize(dimensionBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        return boton;
    }


    // --- GETTERS ---
    public JButton getBtnNuevaPartida() { return btnNuevaPartida; }
    public JButton getBtnCargarPartida() { return btnCargarPartida; }
}