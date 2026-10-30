package vista.paneles;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import utils.CargadorImagenes;

public class PanelSprite extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton btnSprite;
    private JProgressBar barraVida;
    private JProgressBar barraMana;
    // private JProgressBar barraExperiencia;
    private JLabel lblNivel;

    public PanelSprite() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(140, 220));
        setOpaque(false);

        // --- PANEL SUPERIOR: BARRAS ---
        JPanel panelBarras = new JPanel(new GridLayout(2, 1, 0, 2));
        panelBarras.setOpaque(false);
        panelBarras.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        barraVida = new JProgressBar();
        barraVida.setStringPainted(true);
        barraVida.setForeground(new Color(40, 167, 69));
        barraVida.setBackground(Color.DARK_GRAY);
        barraVida.setFont(new Font("SansSerif", Font.BOLD, 10));

        barraMana = new JProgressBar();
        barraMana.setStringPainted(true);
        barraMana.setForeground(new Color(0, 123, 255));
        barraMana.setBackground(Color.DARK_GRAY);
        barraMana.setFont(new Font("SansSerif", Font.BOLD, 10));

        // barraExperiencia = new JProgressBar();
        // barraExperiencia.setStringPainted(true);
        // barraExperiencia.setForeground(new Color(250, 215, 0));
        // barraExperiencia.setBackground(Color.DARK_GRAY);
        // barraExperiencia.setFont(new Font("SansSerif", Font.BOLD, 10));

        panelBarras.add(barraVida);
        panelBarras.add(barraMana);
        // panelBarras.add(barraExperiencia);

        // Inicializar como JButton y hacerlo invisible
        btnSprite = new JButton("SIN SPRITE");
        btnSprite.setHorizontalAlignment(SwingConstants.CENTER);
        btnSprite.setContentAreaFilled(false); // Transparente
        btnSprite.setBorderPainted(false);     // Sin bordes de botón
        btnSprite.setFocusPainted(false);      // Sin línea punteada al clickear
        btnSprite.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muestra la manito al pasar el mouse

        // --- PANEL INFERIOR: NIVEL ---
        lblNivel = new JLabel("LV 1", SwingConstants.LEFT);
        lblNivel.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNivel.setForeground(new Color(255, 255, 255));
        lblNivel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));

        add(panelBarras, BorderLayout.NORTH);
        add(btnSprite, BorderLayout.CENTER);
        add(lblNivel, BorderLayout.SOUTH);
    }

    // Agregar el índice y el ActionListener para comunicar el clic al controlador
    public void actualizar(String rutaSprite, String nombreEntidad, int hpActual, int hpMax, int mpActual, int mpMax, int nivel, int indice, ActionListener listenerClic) {

        barraVida.setMaximum(hpMax);
        barraVida.setValue(hpActual);
        barraVida.setString(String.valueOf(hpActual));

        barraMana.setMaximum(mpMax);
        barraMana.setValue(mpActual);
        barraMana.setString(String.valueOf(mpActual));

        // barraExperiencia.setMaximum((int) xpMax);
        // barraExperiencia.setValue((int) xpActual);
        // barraExperiencia.setString(String.valueOf(xpActual));

        lblNivel.setText("LV " + nivel);

        ImageIcon imagen = CargadorImagenes.cargar(rutaSprite);
        if(imagen != null){
            Image icono = imagen.getImage().getScaledInstance(120, 140, Image.SCALE_FAST);
            btnSprite.setIcon(new ImageIcon(icono));
            btnSprite.setText("");
        } else {
            btnSprite.setIcon(null);
            btnSprite.setText(nombreEntidad);
        }

        // Removemos los anteriores para no duplicar eventos si el panel se actualiza
        for (ActionListener al : btnSprite.getActionListeners()) {
            btnSprite.removeActionListener(al);
        }
        if (listenerClic != null) {
            btnSprite.addActionListener(listenerClic);
        }
    }
}