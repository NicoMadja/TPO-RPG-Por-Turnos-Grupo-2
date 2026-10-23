package vista.pantallas;

import controlador.ControladorBatalla;
import dto.*;
import vista.paneles.PanelAcciones;
import vista.paneles.PanelEscenaBatalla;
import vista.paneles.PanelEstadoEntidad;
import vista.paneles.PanelRegistro;

import java.awt.*;
import javax.swing.*;

public class PantallaBatalla extends JFrame {
    private static final long serialVersionUID = 1L;

    // --- VARIABLES DE LA VISTA ---
    private PanelEstadoEntidad panelJugador;
    private PanelEstadoEntidad panelEnemigo;
    private PanelEscenaBatalla panelEscena;
    private PanelRegistro panelRegistro;
    private PanelAcciones panelAcciones;
    private JPanel panelInferior;

    // --- NUEVO: ETIQUETA DE TURNO Y CONTROLADOR ---
    private JLabel lblTurno;
    private ControladorBatalla controladorBatalla;

    public PantallaBatalla(){
        setTitle("Batalla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100,700);
        setMinimumSize(new Dimension(900,600));
        setLocationRelativeTo(null);

        JPanel contenido = new JPanel(new BorderLayout(12,12));
        contenido.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        contenido.setBackground(new Color(22,26,38));

        // --- CABECERA DE LA PANTALLA ---
        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 0, 4));
        panelNorte.setOpaque(false);

        JLabel titulo = new JLabel("BATALLA", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));

        lblTurno = new JLabel("Iniciando batalla...", SwingConstants.CENTER);
        lblTurno.setForeground(new Color(255, 215, 0)); // Color dorado retro
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 18));

        panelNorte.add(titulo);
        panelNorte.add(lblTurno);

        panelJugador = new PanelEstadoEntidad("Jugador");
        panelEnemigo = new PanelEstadoEntidad("Enemigo");
        panelEscena = new PanelEscenaBatalla();
        panelRegistro = new PanelRegistro();

        panelInferior = new JPanel(new BorderLayout(12,0));

        JPanel centro = new JPanel(new BorderLayout(12,12));
        centro.setOpaque(false);
        centro.add(panelEscena, BorderLayout.CENTER);

        panelInferior.setOpaque(false);
        panelInferior.add(panelRegistro, BorderLayout.CENTER);

        contenido.add(panelNorte, BorderLayout.NORTH); // <--- AGREGAMOS EL PANEL NORTE CON TITULO Y TURNO
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(panelInferior, BorderLayout.SOUTH);
        setContentPane(contenido);
    }

    public void setControlador(ControladorBatalla controlador){
        this.controladorBatalla = controlador;
        panelAcciones = new PanelAcciones(controlador);
        panelInferior.add(panelAcciones, BorderLayout.EAST);
    }

    public void actualizar (EstadoBatallaDTO estado){
        for(PersonajeDTO heroe: estado.getHeroes()){
            panelJugador.actualizar(heroe);
        }
        for(EnemigoDTO enemigo: estado.getEnemigos()){
            panelEnemigo.actualizar(enemigo);
        }

        panelEscena.actualizar(estado, this.controladorBatalla);

        // --- ACTUALIZAR MESAJE DE TURNO Y REGISTRO ---
        String mensajeTurno = construirMensajeTurno(estado);
        lblTurno.setText(mensajeTurno);
        panelRegistro.actualizar(mensajeTurno);

        if(panelAcciones != null){
            panelAcciones.setHabilitado(estado.getFase() != FaseBatalla.FINALIZADA);
        }
    }

    // Método auxiliar actualizado en PantallaBatalla.java
    private String construirMensajeTurno(EstadoBatallaDTO estado) {
        EntidadDTO actual = estado.getEntidadActual();
        FaseBatalla fase = estado.getFase();

        if (fase == FaseBatalla.FINALIZADA) {
            return "¡BATALLA FINALIZADA!";
        }

        if (actual == null) {
            return "Iniciando batalla...";
        }

        // Armamos un encabezado con datos detallados (Nombre, Nivel, Ataque y Defensa)
        String infoEntidad = actual.getNombre() + " [Lv." + actual.getNivel()
                + " | ATQ: " + actual.getAtaqueActual()
                + " | DEF: " + actual.getDefensaActual() + "]";

        switch (fase) {
            case ELIGIENDO_ACCION:
                return "TURNO DE: " + infoEntidad + " -> Elije una acción";
            case ELIGIENDO_ENEMIGO:
                return actual.getNombre() + ": Selecciona un enemigo para atacar";
            case ELIGIENDO_ALIADO:
                return actual.getNombre() + ": Selecciona un aliado como objetivo";
            case ELIGIENDO_CONSUMIBLE:
                return actual.getNombre() + ": Selecciona un consumible del inventario";
            case TURNO_ENEMIGO:
                return "TURNO ENEMIGO: " + infoEntidad + " pensando...";
            default:
                return "";
        }
    }
}