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
    private  JLabel lblParty;
    private List<ConexionDTO> conexiones;
    private List<JButton> botonesNodos;
    private Image imagenFondo; // <-- Aquí guardaremos tu Pixel Art
    private List<NodoDTO> nodos;
    private JButton btnOpciones;
    public PantallaMapa() {
        setLayout(null);
        conexiones = new ArrayList<>();
        botonesNodos = new ArrayList<>();

        btnOpciones = new JButton("⚙ Opciones"); // Usa un emoji o texto
        btnOpciones.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnOpciones.setBounds(860, 20, 140, 40); // Ajusta la X según el ancho real de tu ventana (ej: 1024 - 160)
        btnOpciones.setFocusPainted(false);
        btnOpciones.setBackground(new Color(50, 50, 50));
        btnOpciones.setForeground(Color.WHITE);
        btnOpciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnOpciones);
    }

    public void cargarMapa(List<NodoDTO> nodos, List<ConexionDTO> conexiones, ActionListener listenerClicNodo) {
        this.nodos = nodos;
        this.conexiones = conexiones;
        this.botonesNodos.clear();

        // Al hacer removeAll() borraste el botón de opciones
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
                lblTu.setForeground(Color.BLACK);
                lblTu.setFont(new Font("SansSerif", Font.BOLD, 16));

                lblTu.setBounds(nodo.getX() - 50, nodo.getY() - 65, 100, 20);
                add(lblTu);
                setComponentZOrder(lblTu, 0);
            }
        }

        // --- SOLUCIÓN: VOLVER A AGREGAR EL BOTÓN DE OPCIONES ---
        // Agregamos de nuevo el botón y lo ponemos al frente
        if (btnOpciones != null) {
            add(btnOpciones);
            setComponentZOrder(btnOpciones, 0); // Para que ningún camino ni nodo lo tape
        }

        revalidate();
        repaint();
    }

    public void cambiarFondo(String rutaSprite) {
        ImageIcon iconoFondo = CargadorImagenes.cargar(rutaSprite);

        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        } else {
            imagenFondo = null;
            System.err.println("No se pudo cargar el fondo: " + rutaSprite);
        }

        repaint();
    }

    // Este metodo nativo se ejecuta automáticamente al redimensionar la ventana
    @Override
    public void doLayout() {
        super.doLayout();
        if (nodos == null || botonesNodos == null) return;

        // Resolución base sobre la cual calculaste tus coordenadas exactas (Ej: 1920x1080)
        double anchoBase = 1920.0;
        double altoBase = 1080.0;

        for (int i = 0; i < nodos.size(); i++) {
            NodoDTO dto = nodos.get(i);
            JButton btn = botonesNodos.get(i);

            // Regla de tres simple para escalar la posición
            int xDinamico = (int) ((dto.getX() * getWidth()) / anchoBase);
            int yDinamico = (int) ((dto.getY() * getHeight()) / altoBase);

            btn.setBounds(xDinamico - 25, yDinamico - 25, 50, 50);
        }
        if (lblParty != null) {
            // Reemplaza 890 y 860 por las coordenadas X e Y originales que le pusiste a tu JLabel
            int xDinamicoTu = (int) ((890 * getWidth()) / anchoBase);
            int yDinamicoTu = (int) ((860 * getHeight()) / altoBase);

            lblParty.setBounds(xDinamicoTu, yDinamicoTu, 50, 30);
        }
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

        // 1. Dibuja la imagen estirándola automáticamente al tamaño actual del contenedor
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(34, 139, 34)); // Verde de respaldo
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // 2. Traza los caminos
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);

        for (ConexionDTO conexion : conexiones) {
            int origenX = (int) ((conexion.getOrigenX() * getWidth()) / 1920.0);
            int origenY = (int) ((conexion.getOrigenY() * getHeight()) / 1080.0);
            int destinoX = (int) ((conexion.getDestinoX() * getWidth()) / 1920.0);
            int destinoY = (int) ((conexion.getDestinoY() * getHeight()) / 1080.0);

            g2.drawLine(origenX, origenY, destinoX, destinoY);
        }
    }

    public JButton getBtnOpciones() {
        return btnOpciones;
    }
}