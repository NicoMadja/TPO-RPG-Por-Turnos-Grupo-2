package vista.paneles;

import controlador.ControladorBatalla;
import dto.EstadoBatallaDTO;
import dto.FaseBatalla;

import javax.swing.*;
import java.awt.*;


public class PanelAcciones extends JPanel {
    private final ControladorBatalla controlador;
    private final JButton btnAtacar;
    private final JButton btnHabilidad;
    private final JButton btnDefender;
    private final JButton btnConsumible;
    private final JButton btnCancelar;

    public PanelAcciones(ControladorBatalla controlador) {
        this.controlador = controlador;

        // BOTONES
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

    public void actualizar(EstadoBatallaDTO estado) {
        boolean eligiendoAccion = estado.getFase() == FaseBatalla.ELIGIENDO_ACCION;

        // el && corta antes de consultar al controlador cuando no es el turno de un héroe
        boolean puedeHabilidad = eligiendoAccion && controlador.puedeUsarHabilidad();
        boolean hayItems = !estado.getItems().isEmpty();

        // para poder cancelar
        FaseBatalla fase = estado.getFase();
        boolean eligiendoObjetivo = fase == FaseBatalla.ELIGIENDO_ENEMIGO
                || fase == FaseBatalla.ELIGIENDO_ALIADO
                || fase == FaseBatalla.ELIGIENDO_CONSUMIBLE;

        btnAtacar.setEnabled(eligiendoAccion);
        btnDefender.setEnabled(eligiendoAccion);
        btnHabilidad.setEnabled(puedeHabilidad);
        btnConsumible.setEnabled(eligiendoAccion && hayItems);
        btnCancelar.setEnabled(eligiendoObjetivo);

        // si el botón está apagado por un motivo, se lo contamos al pasar el mouse
        btnHabilidad.setToolTipText(eligiendoAccion && !puedeHabilidad ? "Maná insuficiente" : null);
        btnConsumible.setToolTipText(eligiendoAccion && !hayItems ? "No hay ítems en el inventario" : null);
    }
}
