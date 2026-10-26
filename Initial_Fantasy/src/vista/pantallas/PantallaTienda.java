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
    private JButton btnSalir;

    public PantallaTienda() {
        // En lugar de null, usamos BorderLayout para que los paneles se acomoden solos al redimensionar
        setLayout(new BorderLayout());

        // 1. Cargar fondo
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/tienda.png");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        } else {
            setBackground(new Color(60, 30, 15)); // Color madera por si falla la imagen
        }

        // ==========================================
        // PANEL SUPERIOR (Botón Salir y Oro)
        // ==========================================
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        btnSalir = new JButton("Continuar Viaje");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBackground(new Color(50, 50, 50));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);

        lblOroActual = new JLabel("Oro: 0", SwingConstants.RIGHT);
        lblOroActual.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblOroActual.setForeground(Color.YELLOW);

        panelNorte.add(btnSalir, BorderLayout.WEST);
        panelNorte.add(lblOroActual, BorderLayout.EAST);


        // ==========================================
        // PANEL CENTRAL (El Catálogo en Estantes)
        // ==========================================
        // Creamos un contenedor que limite el tamaño del catálogo
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);

        // Grilla de pociones (2x2)
        panelCatalogo = new JPanel(new GridLayout(2, 2, 40, 40));
        panelCatalogo.setOpaque(false);

        // Ajustamos el tamaño del panel de pociones para que no choque con el tendero
        panelCatalogo.setPreferredSize(new Dimension(500, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST; // Ancla el catálogo a la izquierda
        gbc.insets = new Insets(0, 50, 0, 0); // Empuja un poco desde el borde izquierdo

        panelCentro.add(panelCatalogo, gbc);


        // ==========================================
        // PANEL INFERIOR (Inventario)
        // ==========================================
        panelInventario = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelInventario.setOpaque(false);
        panelInventario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Inventario del Grupo",
                0, 0, new Font("SansSerif", Font.BOLD, 14), Color.WHITE));

        // Lo envolvemos en otro panel para darle márgenes
        JPanel envoltorioSur = new JPanel(new BorderLayout());
        envoltorioSur.setOpaque(false);
        envoltorioSur.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        envoltorioSur.add(panelInventario, BorderLayout.CENTER);


        // Ensamblar
        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(envoltorioSur, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            // Dibuja la imagen cubriendo el JFrame, adaptable a la pantalla
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setControlador(ControladorTienda controlador) {
        this.controlador = controlador;
        for (java.awt.event.ActionListener al : btnSalir.getActionListeners()) {
            btnSalir.removeActionListener(al);
        }
        btnSalir.addActionListener(e -> controlador.salir());
    }

    public void actualizarDatos(int oro, List<ConsumibleDTO> catalogo, List<ConsumibleDTO> inventario) {
        lblOroActual.setText("Oro: " + oro);

        // ==========================================
        // DIBUJAR CATÁLOGO DE POCIONES
        // ==========================================
        panelCatalogo.removeAll();

        for (ConsumibleDTO dto : catalogo) {
            JButton btnComprar = new JButton();
            btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Estilo visual del botón
            btnComprar.setBackground(new Color(20, 10, 5, 200)); // Fondo oscuro más compacto
            btnComprar.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 2));
            btnComprar.setFocusPainted(false);

            // Cargar y dimensionar el Sprite de la poción
            if (dto.getSprite() != null && !dto.getSprite().isEmpty()) {
                ImageIcon iconoOriginal = CargadorImagenes.cargar(dto.getSprite());
                if (iconoOriginal != null) {
                    // Escalamos la poción un poco más grande
                    Image imgEscalada = iconoOriginal.getImage().getScaledInstance(65, 80, Image.SCALE_FAST);
                    btnComprar.setIcon(new ImageIcon(imgEscalada));
                }
            }

            // Nombre y Precio debajo
            btnComprar.setText("<html><center><b>" + dto.getNombre() + "</b><br><font color='#FFD700'>" + dto.getPrecio() + "G</font></center></html>");
            btnComprar.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnComprar.setHorizontalTextPosition(SwingConstants.CENTER);
            btnComprar.setIconTextGap(8);
            btnComprar.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btnComprar.setForeground(Color.WHITE);

            // Evento
            btnComprar.addActionListener(e -> controlador.procesarCompra(dto.getNombre()));

            panelCatalogo.add(btnComprar);
        }

        // Rellenar espacios vacíos para mantener la grilla estable
        int espaciosVacios = 4 - catalogo.size();
        for (int i = 0; i < espaciosVacios; i++) {
            JPanel slotVacio = new JPanel();
            slotVacio.setOpaque(false);
            panelCatalogo.add(slotVacio);
        }

        // ==========================================
        // DIBUJAR INVENTARIO DE POCIONES
        // ==========================================
        panelInventario.removeAll();
        for (ConsumibleDTO dto : inventario) {
            JLabel lblItem = new JLabel(dto.getNombre());
            lblItem.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblItem.setForeground(Color.WHITE);

            if (dto.getSprite() != null && !dto.getSprite().isEmpty()) {
                ImageIcon iconItem = CargadorImagenes.cargar(dto.getSprite());
                if (iconItem != null) {
                    lblItem.setIcon(new ImageIcon(iconItem.getImage().getScaledInstance(30, 35, Image.SCALE_FAST)));
                }
            }
            panelInventario.add(lblItem);
        }

        panelCatalogo.revalidate();
        panelCatalogo.repaint();
        panelInventario.revalidate();
        panelInventario.repaint();
    }
}