package vista.pantallas;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PantallaRecompensa extends JDialog{

    public PantallaRecompensa(JFrame ventanaPadre, double xp, int oro, List<String> mensajesLevelUp, Runnable alContinuar) {
        super(ventanaPadre, "BATALLA FINALIZADA", true); // true = Modal (bloquea lo de atrás)
        setSize(400, 300);
        setLocationRelativeTo(ventanaPadre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(24, 24, 24)); // Fondo oscuro

        // --- PANEL CENTRAL CON LOS TEXTOS ---
        JPanel panelTextos = new JPanel();
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));
        panelTextos.setOpaque(false);
        panelTextos.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        JLabel lblXp = new JLabel("XP obtenido: " + xp);
        lblXp.setForeground(Color.WHITE);
        lblXp.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelTextos.add(lblXp);
        panelTextos.add(Box.createVerticalStrut(10)); // Espacio

        JLabel lblOro = new JLabel("Oro obtenido: " + oro);
        lblOro.setForeground(new Color(255, 215, 0)); // Dorado
        lblOro.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelTextos.add(lblOro);
        panelTextos.add(Box.createVerticalStrut(20));

        // Agregamos un texto por cada personaje que haya subido de nivel
        for (String msg : mensajesLevelUp) {
            JLabel lblLvl = new JLabel(msg);
            lblLvl.setForeground(new Color(40, 167, 69)); // Verde
            lblLvl.setFont(new Font("SansSerif", Font.BOLD, 16));
            panelTextos.add(lblLvl);
            panelTextos.add(Box.createVerticalStrut(5));
        }

        // --- BOTÓN CONTINUAR ---
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnContinuar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnContinuar.addActionListener(e -> {
            dispose(); // 1. Cierra el cartel modal
            if (alContinuar != null) alContinuar.run(); // 2. Ejecuta el viaje de vuelta al mapa
        });

        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelSur.add(btnContinuar);

        add(panelTextos, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }
}

