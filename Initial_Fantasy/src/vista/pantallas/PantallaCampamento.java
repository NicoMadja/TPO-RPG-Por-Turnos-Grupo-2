package vista.pantallas;

import utils.CargadorImagenes;
import javax.swing.*;
import java.awt.*;

public class PantallaCampamento extends JDialog {

    public PantallaCampamento(JFrame ventanaPadre, Runnable alDescansar) {
        super(ventanaPadre, "CAMPAMENTO", true); // true = Modal (bloquea el mapa)
        setSize(550, 500); // Ampliamos un poco el alto para que el sprite luzca bien
        setLocationRelativeTo(ventanaPadre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 35, 20)); // Fondo verde muy oscuro estilo campamento

        // --- PANEL CENTRAL CON SPRITE Y TEXTOS ---
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // 1. JLabel para mostrar el Sprite del Campamento
        JLabel lblSprite = new JLabel();
        lblSprite.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cargamos la imagen utilizando tu clase de utilidades
        // (Asegúrate de guardar tu imagen en src/recursos/sprites/ con este nombre o ajusta la ruta)
        ImageIcon iconoSprite = CargadorImagenes.cargar("/recursos/sprites/campamento.png");
        if (iconoSprite != null) {
            // Escalamos la imagen manteniendo la nitidez tipo pixel art (SCALE_FAST)
            Image imagenEscalada = iconoSprite.getImage().getScaledInstance(450, 290, Image.SCALE_FAST);
            lblSprite.setIcon(new ImageIcon(imagenEscalada));
        } else {
            lblSprite.setText("[ Sprite Campamento No Encontrado ]");
            lblSprite.setForeground(Color.RED);
        }

        panelContenido.add(lblSprite);
        panelContenido.add(Box.createVerticalStrut(15)); // Espacio entre el sprite y los textos

        // 2. Títulos y descripciones
        JLabel lblTitulo = new JLabel("Has encontrado un campamento", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Puedes descansar y recuperar energías.", SwingConstants.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContenido.add(lblTitulo);
        panelContenido.add(Box.createVerticalStrut(8));
        panelContenido.add(lblSubtitulo);

        // --- BOTÓN DESCANSAR ---
        JButton btnDescansar = new JButton("Descansar");
        btnDescansar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnDescansar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDescansar.addActionListener(e -> {
            if (alDescansar != null) {
                alDescansar.run(); // Ejecuta la lógica de curación en el modelo
            }
            dispose(); // Cierra el modal y libera el mapa
        });

        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelSur.add(btnDescansar);

        // Ensamblaje final
        add(panelContenido, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}