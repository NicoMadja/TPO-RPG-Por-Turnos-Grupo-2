package vista.paneles;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class PanelRegistro extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextArea txtRegistro;

    public PanelRegistro(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Registro de Batalla"));

        txtRegistro = new JTextArea(6,40);
        txtRegistro.setEditable(false);
        txtRegistro.setLineWrap(true);
        add(new JScrollPane(txtRegistro),BorderLayout.CENTER);
    }

    public void actualizar(String mensaje){
        txtRegistro.append(mensaje+ "\n");
    }
}
