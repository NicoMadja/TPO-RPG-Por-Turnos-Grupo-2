package vista.pantallas;

import dto.EstadoBatallaDTO;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PantallaBatalla extends JFrame {

    public PantallaBatalla(){
        setTitle("Batalla");
        setSize(1280,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void actualizar(EstadoBatallaDTO estadoBatallaDTO){

    }
}
