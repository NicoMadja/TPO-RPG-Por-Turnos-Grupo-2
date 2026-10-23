package vista.paneles;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class PanelRegistro extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextArea txtRegistro;
    private String ultimoMensajeMostrado = "";
    public PanelRegistro(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Registro de Batalla"));

        txtRegistro = new JTextArea(6,40);
        txtRegistro.setEditable(false);
        txtRegistro.setLineWrap(true);
        add(new JScrollPane(txtRegistro),BorderLayout.CENTER);
    }

    public void actualizar(String mensaje) {
        // Solo lo agregamos si NO está vacío y es DIFERENTE al que ya mostramos
        if (mensaje != null && !mensaje.equals(ultimoMensajeMostrado)) {

            txtRegistro.append(mensaje + "\n");
            ultimoMensajeMostrado = mensaje; // Actualizamos la memoria

            // Opcional y muy útil: Esto hace que el área de texto haga scroll
            // hacia abajo automáticamente cuando se llena de texto.
            txtRegistro.setCaretPosition(txtRegistro.getDocument().getLength());
        }
    }
}
