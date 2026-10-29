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


    public PanelAcciones(ControladorBatalla controlador) {
        this.controlador = controlador;

        setBorder(BorderFactory.createTitledBorder("Acciones"));
        setLayout(new GridLayout(2, 2, 8, 8));

        // BOTONES
        btnAtacar = new JButton("Atacar");
        btnAtacar.addActionListener(e -> controlador.elegirAtacar());
        add(btnAtacar);

        btnHabilidad = new JButton("Habilidad");
        btnHabilidad.addActionListener(e -> controlador.elegirHabilidad());
        add(btnHabilidad);

        btnDefender = new JButton("Defender");
        btnDefender.addActionListener(e -> controlador.elegirDefender());
        add(btnDefender);

        btnConsumible = new JButton("Consumible");
        btnConsumible.addActionListener(e -> controlador.elegirConsumible());
        add(btnConsumible);
    }


    // habilita cada botón solo si esa acción se puede hacer ahora mismo
    public void actualizar(EstadoBatallaDTO estado) {
        boolean eligiendoAccion = estado.getFase() == FaseBatalla.ELIGIENDO_ACCION;

        // el && corta antes de consultar al controlador cuando no es el turno de un héroe
        boolean puedeHabilidad = eligiendoAccion && controlador.puedeUsarHabilidad();
        boolean hayItems = !estado.getItems().isEmpty();

        btnAtacar.setEnabled(eligiendoAccion);
        btnDefender.setEnabled(eligiendoAccion);
        btnHabilidad.setEnabled(puedeHabilidad);
        btnConsumible.setEnabled(eligiendoAccion && hayItems);

        // si el botón está apagado por un motivo, se lo contamos al pasar el mouse
        btnHabilidad.setToolTipText(eligiendoAccion && !puedeHabilidad ? "Maná insuficiente" : null);
        btnConsumible.setToolTipText(eligiendoAccion && !hayItems ? "No hay ítems en el inventario" : null);
    }
}
