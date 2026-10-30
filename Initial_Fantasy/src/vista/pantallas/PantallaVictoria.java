package vista.pantallas;

import utils.CargadorImagenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PantallaVictoria extends JPanel {
    private static final long serialVersionUID = 1L;

    private Image imagenFondo;
    private JButton btnVolverMenu;
    private JLabel lblSpriteJefe;

    public PantallaVictoria() {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 30)); // Fondo base por si falla la imagen

        // 1. (Opcional) Puedes cargar un fondo tipo "Castillo iluminado"
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/victoria.png");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        }

        // --- PANEL CENTRAL: TEXTO Y SPRITE ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);

        JLabel lblVictoria = new JLabel("¡FELICIDADES!");
        lblVictoria.setFont(new Font("SansSerif", Font.BOLD, 52));
        lblVictoria.setForeground(new Color(255, 215, 0)); // Dorado
        lblVictoria.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("¡Has derrotado al Demonio y salvado el reino!");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sprite del jefe derrotado (o un trofeo)
        lblSpriteJefe = new JLabel();
        lblSpriteJefe.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregamos un poco de aire
        panelCentro.add(Box.createVerticalGlue());
        panelCentro.add(lblVictoria);
        panelCentro.add(Box.createVerticalStrut(20));
        panelCentro.add(lblSubtitulo);
        panelCentro.add(Box.createVerticalStrut(40));
        panelCentro.add(lblSpriteJefe); // El sprite va en medio
        panelCentro.add(Box.createVerticalGlue());

        // --- PANEL INFERIOR: BOTÓN DE SALIDA ---
        JPanel panelInferior = new JPanel();
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        btnVolverMenu = new JButton("Volver al Menú Principal");
        btnVolverMenu.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnVolverMenu.setBackground(Color.WHITE);
        btnVolverMenu.setForeground(Color.BLACK);
        btnVolverMenu.setFocusPainted(false);
        btnVolverMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolverMenu.setPreferredSize(new Dimension(300, 50));

        panelInferior.add(btnVolverMenu);

        // --- ENSAMBLAR ---
        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // Dibujamos el fondo si existe
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Un fondo oscuro si no hay imagen
            g.setColor(new Color(15, 10, 20));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * El Controlador llama a este método justo antes de mostrar la pantalla
     * para pasarle el sprite exacto del jefe derrotado y la orden del botón.
     */
    public void configurarVictoria(Runnable accionVolver) {

        // 2. Limpiar listeners fantasmas y asignar la acción del menú
        for (ActionListener al : btnVolverMenu.getActionListeners()) {
            btnVolverMenu.removeActionListener(al);
        }
        btnVolverMenu.addActionListener(e -> accionVolver.run());
    }
}