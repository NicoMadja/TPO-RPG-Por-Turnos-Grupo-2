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
        // 1. Usar GridBagLayout centra automáticamente los componentes en la pantalla
        setLayout(new GridBagLayout());

        // 2. Cargar la imagen de fondo
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/menuFondo.jpg");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        }

        // 3. Crear los botones
        btnNuevaPartida = crearBotonMenu("Nueva Partida");
        btnCargarPartida = crearBotonMenu("Cargar Partida");
        btnSalir = crearBotonMenu("Salir");

        // Acción por defecto del botón Salir
        btnSalir.addActionListener(e -> System.exit(0));

        // 4. Crear un contenedor transparente para apilar los botones verticalmente
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setOpaque(false); // Transparente para no tapar el fondo

        // 5. Agregamos los botones al contenedor con separadores de 20 píxeles
        panelBotones.add(btnNuevaPartida);
        panelBotones.add(Box.createVerticalStrut(20)); // Espacio vacío
        panelBotones.add(btnCargarPartida);
        panelBotones.add(Box.createVerticalStrut(20)); // Espacio vacío
        panelBotones.add(btnSalir);

        // 6. Agregamos el grupo de botones al panel principal (quedará centrado)
        add(panelBotones);
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

        // NUEVO: Le fijamos el tamaño exacto al botón para que el BoxLayout lo respete
        Dimension dimensionBoton = new Dimension(240, 50);
        boton.setPreferredSize(dimensionBoton);
        boton.setMaximumSize(dimensionBoton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón dentro de su propia columna

        return boton;
    }

    // --- GETTERS ---
    public JButton getBtnNuevaPartida() { return btnNuevaPartida; }
    public JButton getBtnCargarPartida() { return btnCargarPartida; }
}