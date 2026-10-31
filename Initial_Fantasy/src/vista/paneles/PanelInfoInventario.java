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
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

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

        actualizarInformacion(estado);
        actualizarInventario(estado);

        panelBotonesItems.revalidate();
        panelBotonesItems.repaint();
    }

    private void actualizarInformacion(EstadoBatallaDTO estado) {

        String mensaje = estado.getMensajeInfo();

        if (mensaje == null || mensaje.isBlank()) mensaje = "Esperando acción...";

        lblTitulo.setText(obtenerTitulo(estado.getFase()));
        txtInfo.setText(mensaje);
    }

    private String obtenerTitulo(FaseBatalla fase) {

        switch (fase) {
            case ELIGIENDO_ACCION:
                return "INFORMACIÓN";

            case ELIGIENDO_ENEMIGO:
                return "OBJETIVO ENEMIGO";

            case ELIGIENDO_ALIADO:
                return "OBJETIVO ALIADO";

            case ELIGIENDO_CONSUMIBLE:
                return "INVENTARIO";

            case MOSTRANDO_RESULTADO:
                return "RESULTADO";

            case TURNO_ENEMIGO:
                return "TURNO ENEMIGO";

            case FINALIZADA:
                return "BATALLA FINALIZADA";

            default:
                return "INFORMACIÓN";
        }
    }

    private void actualizarInventario(EstadoBatallaDTO estado) {
        panelBotonesItems.removeAll();
        if (estado.getFase() != FaseBatalla.ELIGIENDO_CONSUMIBLE) return;

        for (ConsumibleDTO consumible : estado.getItems()) {
            JButton btn = new JButton(consumible.getNombre());
            btn.addActionListener(e ->
                    controlador.elegirConsumible(consumible.getNombre())
            );

            panelBotonesItems.add(btn);
        }
    }
}