package vista.pantallas;

import controlador.ControladorTienda;
import dto.ConsumibleDTO;
import utils.CargadorImagenes;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PantallaTienda extends JPanel {
    private static final long serialVersionUID = 1L;

    private ControladorTienda controlador;
    private Image imagenFondo;
    private JPanel panelCatalogo;
    private JPanel panelInventario;
    private JLabel lblOroActual;
    private JLabel lblMensaje;
    private JButton btnSalir;
    private JPanel panelCentro;

    public PantallaTienda() {
        setLayout(new BorderLayout());
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/tienda.png");

        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        } else {
            setBackground(new Color(60, 30, 15));
        }

        // panel de oro
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 30));

        lblOroActual = new JLabel("Oro: 0", SwingConstants.RIGHT);
        lblOroActual.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblOroActual.setForeground(Color.YELLOW);

        panelNorte.add(lblOroActual, BorderLayout.EAST);

        // mensaje de compra exitos u oro insuficiente
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        panelNorte.add(lblMensaje, BorderLayout.WEST);

        // panel central
        panelCentro = new JPanel(null);
        panelCentro.setOpaque(false);

        // catalogo de 4 estantes
        panelCatalogo = new JPanel(new GridLayout(2, 2, 18, 18));
        panelCatalogo.setOpaque(false);
        panelCatalogo.setSize(850, 400);
        panelCatalogo.setBounds(30, 15, 850, 400);
        panelCentro.add(panelCatalogo);

        // panel inventario
        panelInventario = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelInventario.setOpaque(false);
        panelInventario.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                new Color(212, 175, 55)), "Inventario del Grupo", 0, 0,
                new Font("SansSerif", Font.BOLD, 14), Color.WHITE)
        );

        // boton continuar viaje
        btnSalir = new JButton("Continuar Viaje");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBackground(new Color(55, 28, 16));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setOpaque(true);
        btnSalir.setContentAreaFilled(true);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color
                (212, 175, 55), 2), BorderFactory.createEmptyBorder(10, 18, 10, 18)));


        // ensamblar panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout(15, 0));
        panelInferior.setOpaque(false);
        panelInferior.add(panelInventario, BorderLayout.CENTER);
        panelInferior.add(btnSalir, BorderLayout.EAST);

        JPanel envoltorioSur = new JPanel(new BorderLayout());
        envoltorioSur.setOpaque(false);
        envoltorioSur.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        envoltorioSur.add(panelInferior, BorderLayout.CENTER);


        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(envoltorioSur, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagenFondo != null) g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

    public void setControlador(ControladorTienda controlador) {
        this.controlador = controlador;

        for (java.awt.event.ActionListener al : btnSalir.getActionListeners()) {
            btnSalir.removeActionListener(al);
        }

        btnSalir.addActionListener(e -> this.controlador.salir());
    }

    public void mostrarMensaje(String texto) {
        lblMensaje.setText(texto == null || texto.isEmpty() ? " " : texto);
    }

    public void actualizarDatos(int oro, List<ConsumibleDTO> catalogo, List<ConsumibleDTO> inventario) {
        lblOroActual.setText("Oro: " + oro);

        // catálogo de pociones
        panelCatalogo.removeAll();
        for (ConsumibleDTO dto : catalogo) {
            JButton btnComprar = new JButton();
            btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnComprar.setBackground(new Color(35, 17, 9));
            btnComprar.setOpaque(true);
            btnComprar.setContentAreaFilled(true);
            btnComprar.setFocusPainted(false);
            btnComprar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color
                    (212, 175, 55), 2), BorderFactory.createEmptyBorder(
                            5, 5, 5, 5)));

            // Sprite
            if (dto.getSprite() != null && !dto.getSprite().isEmpty()) {
                ImageIcon icono = CargadorImagenes.cargar(dto.getSprite());
                if (icono != null) {
                    Image imagen = icono.getImage().getScaledInstance(45, 55, Image.SCALE_SMOOTH);
                    btnComprar.setIcon(new ImageIcon(imagen));
                }
            }

            // Nombre y precio
            btnComprar.setText("<html><center><b>" + dto.getNombre() + "</b><br><font color='#FFD700'>" + dto.getPrecio()
                    + "G</font></center></html>");
            btnComprar.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnComprar.setHorizontalTextPosition(SwingConstants.CENTER);
            btnComprar.setIconTextGap(5);
            btnComprar.setFont(new Font("SansSerif", Font.PLAIN, 12));
            btnComprar.setForeground(Color.WHITE);
            btnComprar.addActionListener(e -> controlador.procesarCompra(dto.getNombre()));
            panelCatalogo.add(btnComprar);
        }

        panelCatalogo.revalidate();
        panelCatalogo.repaint();

        // inventario de la party
        panelInventario.removeAll();
        for (ConsumibleDTO dto : inventario) {
            JLabel lblItem = new JLabel(dto.getNombre());
            lblItem.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblItem.setForeground(Color.WHITE);
            if (dto.getSprite() != null && !dto.getSprite().isEmpty()) {
                ImageIcon icono = CargadorImagenes.cargar(dto.getSprite());
                if (icono != null) {
                    Image imagen = icono.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
                    lblItem.setIcon(new ImageIcon(imagen));
                }
            }

            panelInventario.add(lblItem);
        }

        panelInventario.revalidate();
        panelInventario.repaint();
    }
}