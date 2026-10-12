package vista.paneles;

import controlador.ControladorBatalla;

import javax.swing.*;
import java.awt.*;

public class PanelAcciones extends JPanel {
    private final JButton btnAtacar;
    private final JButton btnHabilidad;
    private final JButton btnDefender;
    private final JButton btnConsumible;


    public PanelAcciones(ControladorBatalla controlador) {
        setBorder(BorderFactory.createTitledBorder("Acciones"));

        setLayout(new GridLayout(2, 2, 8, 8));

        // BOTONES
        btnAtacar = new JButton("Atacar");
        btnAtacar.addActionListener(e -> controlador.atacar());
        add(btnAtacar);

        btnHabilidad = new JButton("Habilidad");
        btnHabilidad.addActionListener(e -> controlador.usarHabilidad());
        add(btnHabilidad);

        btnDefender = new JButton("Defender");
        btnDefender.addActionListener(e -> controlador.defender());
        add(btnDefender);

        btnConsumible = new JButton("Consumible");
        btnConsumible.addActionListener(e -> controlador.usarConsumible());
        add(btnConsumible);
    }


    public void setHabilitado(boolean habilitado) {
        btnAtacar.setEnabled(habilitado);
        btnHabilidad.setEnabled(habilitado);
        btnDefender.setEnabled(habilitado);
        btnConsumible.setEnabled(habilitado);
    }
}
