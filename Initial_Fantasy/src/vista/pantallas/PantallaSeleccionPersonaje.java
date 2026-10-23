package vista.pantallas;

import dto.PersonajeDTO;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PantallaSeleccionPersonaje extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<String> personajesSeleccionados; // Aquí guardarías los DTOs o IDs en realidad
    private JButton btnContinuar;
    private JPanel panelCuadricula;

    public PantallaSeleccionPersonaje() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));

        personajesSeleccionados = new ArrayList<>();

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

    private List<PersonajeDTO> personajesSeleccionado = new ArrayList<>();

    // El método recibe DTOs
    public void cargarPersonajes(List<PersonajeDTO> heroesDisponibles) {
        panelCuadricula.removeAll();
        personajesSeleccionados.clear();
        btnContinuar.setEnabled(false);

        for (PersonajeDTO dto : heroesDisponibles) {
            JToggleButton btnPersonaje = new JToggleButton(dto.getNombre());
            // ... (resto de configuración visual) ...

            btnPersonaje.addActionListener(e -> {
                if (btnPersonaje.isSelected()) {
                    if (personajesSeleccionados.size() < 3) {
                        personajesSeleccionados.add(dto.getNombre()); // Guardas el DTO
                        btnPersonaje.setBackground(new Color(100, 255, 100));
                    } else {
                        btnPersonaje.setSelected(false);
                    }
                } else {
                    personajesSeleccionados.remove(dto.getNombre()); // Remueves el DTO
                    btnPersonaje.setBackground(null);
                }
                btnContinuar.setEnabled(personajesSeleccionados.size() == 3);
            });
            panelCuadricula.add(btnPersonaje);
        }
        panelCuadricula.revalidate();
        panelCuadricula.repaint();
    }

    // Devuelve los DTOs elegidos
    public List<PersonajeDTO> getPersonajesSeleccionados() {
        return personajesSeleccionado;
    }
    public JButton getBtnContinuar() {
        return btnContinuar;
    }
}