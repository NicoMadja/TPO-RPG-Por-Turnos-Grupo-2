package app;

import controlador.ControladorJuego;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControladorJuego());
    }
}
