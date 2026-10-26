package app;

import controlador.ControladorJuego;

import javax.swing.*;

public class Juego {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControladorJuego());
    }
}
