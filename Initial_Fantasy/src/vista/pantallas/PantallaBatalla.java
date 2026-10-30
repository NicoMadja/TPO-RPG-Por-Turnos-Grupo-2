package vista.pantallas;

import controlador.ControladorBatalla;
import dto.*;
import vista.paneles.*;

import java.awt.*;
import javax.swing.*;

public class PantallaBatalla extends JPanel {
    private static final long serialVersionUID = 1L;

    private PanelEscenaBatalla panelEscena;
    private PanelRegistro panelRegistro;
    private PanelAcciones panelAcciones;
    private JPanel panelInferior;
    private PanelInfoInventario panelInfo;
    private JLabel lblOrdenTurnos;
    private JLabel lblTurno;
    private ControladorBatalla controladorBatalla;

    public PantallaBatalla() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setBackground(new Color(22, 26, 38));


        // --- CABECERA DE LA PANTALLA ---
        JPanel panelNorte = new JPanel(new GridLayout(3, 1, 0, 4));
        panelNorte.setOpaque(false);

        JLabel titulo = new JLabel("BATALLA", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));

        lblTurno = new JLabel("Iniciando batalla...", SwingConstants.CENTER);
        lblTurno.setForeground(new Color(255, 215, 0));
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 18));

        lblOrdenTurnos = new JLabel("", SwingConstants.CENTER);
        lblOrdenTurnos.setForeground(Color.GREEN);
        lblOrdenTurnos.setFont(new Font("SansSerif", Font.BOLD, 14));

        panelNorte.add(titulo);
        panelNorte.add(lblOrdenTurnos);
        panelNorte.add(lblTurno);


        // --- INICIALIZACIÓN DE PANELES ---
        panelEscena = new PanelEscenaBatalla();
        panelRegistro = new PanelRegistro();
        panelInfo = new PanelInfoInventario();

        panelInferior = new JPanel(new BorderLayout(12, 0));
        panelInferior.setOpaque(false);
        panelInferior.add(panelRegistro, BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout(12, 12));
        centro.setOpaque(false);
        centro.add(panelEscena, BorderLayout.CENTER);
        centro.add(panelInfo, BorderLayout.WEST);

        add(panelNorte, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public void setControlador(ControladorBatalla controlador) {
        this.controladorBatalla = controlador;
        panelInfo.setControlador(controlador);

        panelAcciones = new PanelAcciones(controlador);
        panelInferior.add(panelAcciones, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    public void actualizar(EstadoBatallaDTO estado) {
        String textoOrden = "Orden de Turnos: " + String.join(" ➔ ", estado.getOrdenTurnos());
        lblOrdenTurnos.setText(textoOrden);
        panelEscena.actualizar(estado, this.controladorBatalla);
        panelInfo.actualizar(estado);

        // --- ACTUALIZAR MENSAJE DE TURNO Y REGISTRO ---
        String mensajeTurno = construirMensajeTurno(estado);
        lblTurno.setText(mensajeTurno);
        for (String mensaje : utils.BitacoraBatalla.retirarPendientes()) {
            panelRegistro.agregar(mensaje);
        }
        if (panelAcciones != null) {
            panelAcciones.actualizar(estado);
        }
    }

    private String construirMensajeTurno(EstadoBatallaDTO estado) {
        EntidadDTO actual = estado.getEntidadActual();
        FaseBatalla fase = estado.getFase();
        if (fase == FaseBatalla.FINALIZADA) return "¡BATALLA FINALIZADA!";
        if (actual == null) return "Iniciando batalla...";

        String infoEntidad = actual.getNombre()
                + " [Lv." + actual.getNivel()
                + " | ATQ: " + actual.getAtaqueActual()
                + " | DEF: " + actual.getDefensaActual() + "]";

        switch (fase) {
            case ELIGIENDO_ACCION:
                return infoEntidad + ". Elegí una acción";
            case ELIGIENDO_ENEMIGO:
                return actual.getNombre() + ": Seleccioná un enemigo para atacar";
            case ELIGIENDO_ALIADO:
                return actual.getNombre() + ": Seleccioná un aliado como objetivo";
            case ELIGIENDO_CONSUMIBLE:
                return actual.getNombre() + ": Seleccioná un consumible del inventario";
            case MOSTRANDO_RESULTADO:
                return "Resolviendo acción...";
            case TURNO_ENEMIGO:
                return infoEntidad + " pensando...";
            default:
                return "";
        }
    }
}
