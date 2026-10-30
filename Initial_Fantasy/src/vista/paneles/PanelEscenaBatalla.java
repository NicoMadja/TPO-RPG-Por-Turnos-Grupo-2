package vista.paneles;

import java.awt.*;
import javax.swing.*;

import controlador.ControladorBatalla;
import dto.EnemigoDTO;
import dto.EstadoBatallaDTO;
import dto.PersonajeDTO;
import utils.CargadorImagenes;

public class PanelEscenaBatalla extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel contenedorJugadores;
    private JPanel contenedorEnemigos;

    // NUEVA VARIABLE: Para guardar la imagen de fondo
    private Image imagenFondo;

    public PanelEscenaBatalla(){
        setLayout(new GridLayout(1, 2, 50, 0));

        // Es MUY importante que el panel sea "opaco" para que pinte el fondo,
        // o quitar el setOpaque(false) si lo tenías.
        setOpaque(true);

        // NUEVO: Cargar la imagen de fondo usando tu clase utilitaria.
        // ¡RECUERDA CAMBIAR ESTA RUTA POR EL NOMBRE REAL DE TU IMAGEN DE FONDO!
        ImageIcon iconoFondo = CargadorImagenes.cargar("/recursos/sprites/fondo_castillo.jpg");
        if (iconoFondo != null) {
            imagenFondo = iconoFondo.getImage();
        }

        contenedorJugadores = new JPanel();
        contenedorJugadores.setLayout(new BoxLayout(contenedorJugadores, BoxLayout.Y_AXIS));
        contenedorJugadores.setOpaque(false); // Estos siguen transparentes para no tapar el fondo

        contenedorEnemigos = new JPanel();
        contenedorEnemigos.setLayout(new BoxLayout(contenedorEnemigos, BoxLayout.Y_AXIS));
        contenedorEnemigos.setOpaque(false); // Estos siguen transparentes para no tapar el fondo

        add(contenedorJugadores);
        add(contenedorEnemigos);
    }

    public void actualizar(EstadoBatallaDTO estado, ControladorBatalla controlador){
        contenedorJugadores.removeAll();
        contenedorEnemigos.removeAll();

        // Bucle de héroes con for-each
        int indiceAliadoContador = 0;
        for(PersonajeDTO heroe : estado.getHeroes()){
            PanelSprite sprite = new PanelSprite();
            sprite.setAlignmentX(Component.CENTER_ALIGNMENT);
            sprite.setMaximumSize(new Dimension(140, 220));

            // Guardamos el índice actual en una variable final para poder usarlo en la expresión lambda (el clic)
            final int indiceActual = indiceAliadoContador;

            sprite.actualizar(
                    heroe.getSprite(), heroe.getNombre(), heroe.getVidaActual(), heroe.getVidaMaxima(),
                    heroe.getManaActual(), heroe.getManaMaximo(), heroe.getNivel(), indiceActual,
                    e -> controlador.elegirAliado(indiceActual)
            );

            contenedorJugadores.add(sprite);
            contenedorJugadores.add(javax.swing.Box.createVerticalStrut(10));

            indiceAliadoContador++; // Aumentamos el índice para el próximo héroe
        }

        // Bucle de enemigos con for-each
        int indiceEnemigoContador = 0;
        for(EnemigoDTO enemigo : estado.getEnemigos()){
            PanelSprite sprite = new PanelSprite();
            sprite.setAlignmentX(Component.CENTER_ALIGNMENT);
            sprite.setMaximumSize(new Dimension(140, 220));

            final int indiceActual = indiceEnemigoContador;

            sprite.actualizar(
                    enemigo.getSprite(), enemigo.getNombre(), enemigo.getVidaActual(), enemigo.getVidaMaxima(),
                    enemigo.getManaActual(), enemigo.getManaMaximo(), enemigo.getNivel(), indiceActual,
                    e -> controlador.elegirEnemigo(indiceActual)
            );

            contenedorEnemigos.add(sprite);
            contenedorEnemigos.add(javax.swing.Box.createVerticalStrut(10));

            indiceEnemigoContador++; // Aumentamos el índice para el próximo enemigo
        }

        contenedorJugadores.revalidate();
        contenedorJugadores.repaint();
        contenedorEnemigos.revalidate();
        contenedorEnemigos.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Siempre llamar al super primero

        // Si la imagen cargó correctamente, la dibujamos
        if (imagenFondo != null) {
            // El getWidth() y getHeight() hacen que la imagen se estire
            // automáticamente para cubrir todo el panel, sin importar el tamaño de la ventana.
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}