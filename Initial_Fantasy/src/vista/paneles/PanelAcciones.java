package vista.paneles;

import controlador.ControladorBatalla;

import javax.swing.*;
import java.awt.*;

public class PanelAcciones extends JPanel {

    private final JButton btnAtacar;
    private final JButton btnHabilidad;
    private final JButton btnDefender;
    private final JButton btnConsumible;
    private final JButton btnCancelar;

    public PanelAcciones(ControladorBatalla controlador) {

        setBorder(BorderFactory.createTitledBorder("Acciones"));
        setLayout(new GridLayout(3, 2, 8, 8));

        // Botón Atacar
        btnAtacar = new JButton("Atacar");
        btnAtacar.addActionListener(e -> controlador.elegirAtacar());
        add(btnAtacar);

        // Botón Habilidad
        btnHabilidad = new JButton("Habilidad");
        btnHabilidad.addActionListener(e -> controlador.elegirHabilidad());
        add(btnHabilidad);

        // Botón Defender
        btnDefender = new JButton("Defender");
        btnDefender.addActionListener(e -> controlador.elegirDefender());
        add(btnDefender);

        // Botón Consumible
        btnConsumible = new JButton("Consumible");
        btnConsumible.addActionListener(e -> controlador.elegirConsumible());
        add(btnConsumible);

        // Botón Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> controlador.cancelar());
        btnCancelar.setEnabled(false);
        add(btnCancelar);
    }

    /**
     * Habilita o deshabilita los botones de acciones.
     */
    public void setHabilitado(boolean habilitado) {
        btnAtacar.setEnabled(habilitado);
        btnHabilidad.setEnabled(habilitado);
        btnDefender.setEnabled(habilitado);
        btnConsumible.setEnabled(habilitado);
    }

    /**
     * Habilita o deshabilita la habilidad según las reglas del combate.
     */
    public void setHabilidadHabilitada(boolean habilitada) {
        btnHabilidad.setEnabled(habilitada);
    }

    /**
     * Habilita o deshabilita el botón Cancelar.
     */
    public void setCancelarHabilitado(boolean habilitado) {
        btnCancelar.setEnabled(habilitado);
    }
}