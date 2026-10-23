package vista.pantallas;

import dto.ConexionDTO;
import dto.NodoDTO;
import utils.CargadorImagenes;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PantallaMapa extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<ConexionDTO> conexiones;
    private List<JButton> botonesNodos;
    private Image imagenFondo; // <-- Aquí guardaremos tu Pixel Art

    public PantallaMapa() {
        setLayout(null);
        conexiones = new ArrayList<>();
        botonesNodos = new ArrayList<>();

        // Cargar el pixel art del mapa (Asegúrate de que la ruta sea correcta)
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/Mapa1.png");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        }
    }

    public void cargarMapa(List<NodoDTO> nodos, List<ConexionDTO> conexiones, ActionListener listenerClicNodo) {
        this.conexiones = conexiones;
        this.botonesNodos.clear();
        this.removeAll();

        // Crear los botones (Nodos)
        for (NodoDTO nodo : nodos) {
            JButton btnNodo = crearBotonNodo(nodo);
            btnNodo.setActionCommand(String.valueOf(nodo.getId()));
            if (listenerClicNodo != null) {
                btnNodo.addActionListener(listenerClicNodo);
            }
            botonesNodos.add(btnNodo);
            add(btnNodo);
            if (nodo.isEsActual()) {
                JLabel lblTu = new JLabel("▼ TÚ ▼", SwingConstants.CENTER);
                lblTu.setForeground(Color.WHITE);
                lblTu.setFont(new Font("SansSerif", Font.BOLD, 16));

                // Lo posicionamos justo arriba del botón (ajusta la Y restando píxeles)
                lblTu.setBounds(nodo.getX() -50, nodo.getY() - 50, 100, 20);
                add(lblTu);
            }
        }

        revalidate();
        repaint();
    }

    private JButton crearBotonNodo(NodoDTO nodo) {
        JButton btn = new JButton();
        // Tamaño del botón (ajústalo al tamaño de los círculos negros de tu dibujo)
        btn.setBounds(nodo.getX() - 25, nodo.getY() - 25, 50, 50);

        // Colores según el tipo de nodo (puedes cambiarlos por íconos luego)
        switch (nodo.getTipo()) {
            case "BATALLA": btn.setBackground(new Color(220, 50, 50)); break;
            case "TIENDA": btn.setBackground(new Color(255, 200, 50)); break;
            case "CAMPAMENTO": btn.setBackground(new Color(50, 200, 50)); break;
            case "JEFE": btn.setBackground(new Color(150, 0, 150)); break;
            default: btn.setBackground(Color.GRAY); break;
        }

        btn.setEnabled(nodo.isHabilitado());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Primero dibujamos el Pixel Art de fondo
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fondo verde por si no carga la imagen
            g.setColor(new Color(34, 139, 34));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // 2. Luego dibujamos las líneas sobre el fondo
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK); // Líneas negras como en tu boceto

        for (ConexionDTO conexion : conexiones) {
            g2.drawLine(
                    conexion.getOrigenX(), conexion.getOrigenY(),
                    conexion.getDestinoX(), conexion.getDestinoY()
            );
        }
    }
}