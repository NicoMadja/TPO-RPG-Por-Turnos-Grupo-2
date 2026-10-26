package vista.pantallas;

import dto.PersonajeDTO;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PantallaSeleccionPersonaje extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<PersonajeDTO> personajesSeleccionados;
    private List<String> personajeSeleccionado;
    private JButton btnContinuar;
    private JPanel panelCuadricula;

    public PantallaSeleccionPersonaje() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));

        personajesSeleccionados = new ArrayList<>();
        personajeSeleccionado = new ArrayList<>();

        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("ELIGE A 3 HÉROES PARA TU AVENTURA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // --- CUADRÍCULA DE PERSONAJES ---
        panelCuadricula = new JPanel(new GridLayout(2, 3, 15, 15)); // 2 filas, 3 columnas (6 personajes de ejemplo)
        panelCuadricula.setOpaque(false);
        panelCuadricula.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        add(panelCuadricula, BorderLayout.CENTER);

        // --- BOTÓN CONTINUAR ---
        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        btnContinuar = new JButton("COMENZAR AVENTURA");
        btnContinuar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnContinuar.setEnabled(false); // Deshabilitado por defecto
        btnContinuar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelSur.add(btnContinuar);

        add(panelSur, BorderLayout.SOUTH);
    }



    // El método recibe DTOs
    public void cargarPersonajes(List<PersonajeDTO> heroesDisponibles) {
        panelCuadricula.removeAll();
        personajeSeleccionado.clear();
        personajesSeleccionados.clear();
        btnContinuar.setEnabled(false);

        for (PersonajeDTO dto : heroesDisponibles) {
            JToggleButton btnPersonaje = new JToggleButton(dto.getNombre());
            btnPersonaje.setFont(new Font("SansSerif", Font.BOLD, 16));
            btnPersonaje.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // 1. Cargar el sprite desde el DTO
            String rutaSprite = dto.getSprite(); // Verifica si en tu DTO se llama getSprite() o getRutaSprite()
            if (rutaSprite != null && !rutaSprite.isEmpty()) {
                java.net.URL imgUrl = getClass().getResource(rutaSprite);
                if (imgUrl != null) {
                    btnPersonaje.setIcon(new ImageIcon(imgUrl));
                }
            }

            // 2. Posicionar el texto arriba de la imagen (Como en el boceto)
            btnPersonaje.setVerticalTextPosition(SwingConstants.TOP);
            btnPersonaje.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPersonaje.setIconTextGap(15); // Espacio en píxeles entre el nombre y el dibujo

            btnPersonaje.addActionListener(e -> {
                if (btnPersonaje.isSelected()) {
                    if (personajeSeleccionado.size() < 3) {
                        personajeSeleccionado.add(dto.getNombre()); // Guardas el ID o Nombre
                        personajesSeleccionados.add(dto);
                        btnPersonaje.setBackground(new Color(100, 255, 100));
                    } else {
                        btnPersonaje.setSelected(false);
                    }
                } else {
                    personajeSeleccionado.remove(dto.getNombre());
                    personajesSeleccionados.remove(dto);
                    btnPersonaje.setBackground(null);
                }
                btnContinuar.setEnabled(personajeSeleccionado.size() == 3);
            });
            panelCuadricula.add(btnPersonaje);
        }
        panelCuadricula.revalidate();
        panelCuadricula.repaint();
    }

    // Devuelve los DTOs elegidos
    public List<PersonajeDTO> getPersonajesSeleccionados() {
        return personajesSeleccionados;
    }
    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}