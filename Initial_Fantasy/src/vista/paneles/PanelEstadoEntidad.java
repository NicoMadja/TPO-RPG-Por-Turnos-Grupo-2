package vista.paneles;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import dto.EntidadDTO;
import dto.PersonajeDTO;

public class PanelEstadoEntidad extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel lblNombre;
    private JProgressBar barraVida;
    private JProgressBar barraMana;
    private JLabel lblEstadisticas;

    public PanelEstadoEntidad(String titulo){
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder(titulo));

        lblNombre = new JLabel("-",SwingConstants.CENTER);
        lblNombre.setFont((new Font("SansSerif",Font.BOLD, 18)));
        barraVida = new JProgressBar();
        barraVida.setStringPainted(true);
        barraMana = new JProgressBar();
        barraMana.setStringPainted(true);
        lblEstadisticas = new JLabel("-",SwingConstants.CENTER);
        lblEstadisticas.setFont((new Font("SansSerif",Font.PLAIN, 12)));

        JPanel datos = new JPanel(new GridLayout(4,1,4,4));
        datos.add(lblNombre);
        datos.add(barraVida);
        datos.add(barraMana);
        datos.add(lblEstadisticas);

        add(datos, BorderLayout.CENTER);


    }
    public void actualizar(EntidadDTO personaje){
        lblNombre.setText(personaje.getNombre());
        barraVida.setMaximum(personaje.getVidaMaxima());
        barraVida.setValue(personaje.getVidaActual());
        barraMana.setMaximum(personaje.getManaMaximo());
        barraMana.setValue(personaje.getManaActual());
        barraVida.setString("HP "+ personaje.getVidaActual()+ "/" + personaje.getVidaMaxima());
        barraMana.setString("MP "+ personaje.getManaActual()+ "/" + personaje.getManaMaximo());
        lblEstadisticas.setText("ATQ " + personaje.getAtaqueActual() + "DEF " +personaje.getDefensaActual());
    }
}
