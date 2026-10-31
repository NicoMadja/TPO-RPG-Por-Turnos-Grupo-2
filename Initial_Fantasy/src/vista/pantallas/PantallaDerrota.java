package vista.pantallas;

import javax.swing.*;
import java.awt.*;

public class PantallaDerrota extends JDialog{

    public PantallaDerrota(JFrame ventanaPadre, Runnable alReiniciar) {
        super(ventanaPadre, "BATALLA FINALIZADA", true);
        setSize(400, 300);
        setLocationRelativeTo(ventanaPadre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 10, 10));

        // --- PANEL CENTRAL: GAME OVER ---
        JPanel panelTextos = new JPanel();
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));
        panelTextos.setOpaque(false);
        panelTextos.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        JLabel lblGameOver = new JLabel("Game Over", SwingConstants.CENTER);
        lblGameOver.setForeground(Color.RED);
        lblGameOver.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblGameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTextos.add(lblGameOver);

        // --- BOTÓN CONTINUAR (Volver al menú) ---
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnContinuar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnContinuar.addActionListener(e -> {
            dispose();
            if (alReiniciar != null) alReiniciar.run();
        });

        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelSur.add(btnContinuar);

        add(panelTextos, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}

