package vista.paneles;

import java.awt.*;
import javax.swing.*;
import dto.EstadoBatallaDTO;
import dto.ConsumibleDTO;
import controlador.ControladorBatalla;
import dto.FaseBatalla;

public class PanelInfoInventario extends JPanel {
    private JLabel lblTitulo;
    private JTextArea txtInfo;
    private JPanel panelBotonesItems;
    private ControladorBatalla controlador;

    public PanelInfoInventario() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(220, 0));
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        lblTitulo = new JLabel("INFO", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        txtInfo = new JTextArea("Selecciona una acción.");
        txtInfo.setEditable(false);
        txtInfo.setOpaque(false);
        txtInfo.setForeground(Color.WHITE);
        txtInfo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);

        panelBotonesItems = new JPanel();
        panelBotonesItems.setLayout(new BoxLayout(panelBotonesItems, BoxLayout.Y_AXIS));
        panelBotonesItems.setOpaque(false);

        add(lblTitulo, BorderLayout.NORTH);
        add(txtInfo, BorderLayout.CENTER);
        add(new JScrollPane(panelBotonesItems), BorderLayout.SOUTH);
    }

    public void setControlador(ControladorBatalla controlador) {
        this.controlador = controlador;
    }

    public void actualizar(EstadoBatallaDTO estado) {
        panelBotonesItems.removeAll();
        FaseBatalla fase = estado.getFase();

        if (fase == fase.ELIGIENDO_ENEMIGO) {
            lblTitulo.setText("ATAQUE/HABILIDAD");
            txtInfo.setText("Selecciona un objetivo enemigo.\n\nEl daño final dependerá de la defensa del objetivo.");
        } else if (fase == fase.ELIGIENDO_CONSUMIBLE) {
            lblTitulo.setText("INVENTARIO");
            txtInfo.setText("Haz clic en un ítem para usarlo:");

            for (ConsumibleDTO c : estado.getItems()) {
                JButton btnItem = new JButton(c.getNombre());
                btnItem.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnItem.addActionListener(e -> controlador.elegirConsumible(c.getNombre()));
                panelBotonesItems.add(btnItem);
                panelBotonesItems.add(Box.createVerticalStrut(5));
            }
        } else {
            lblTitulo.setText("INFO");
            txtInfo.setText("Esperando acción...");
        }

        revalidate();
        repaint();
    }
}