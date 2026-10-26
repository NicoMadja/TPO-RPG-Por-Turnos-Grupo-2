
package app;

import controlador.ControladorBatalla;
import modelo.consumibles.Consumible;
import modelo.consumibles.PocionSalud;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.enemigos.Esqueleto;
import modelo.entidades.enemigos.Murcielago;
import modelo.entidades.personajes.Arquero;
import modelo.entidades.personajes.Guerrero;
import modelo.entidades.personajes.Personaje;
import modelo.entidades.personajes.Tanque;
import modelo.batalla.Batalla;
import modelo.party.Inventario;
import modelo.party.Party;
import vista.pantallas.PantallaBatalla;
import vista.pantallas.PantallaMenu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TestBatalla {
    public static void main(String[] args) {
        System.out.println("Prueba en Main: " + TestBatalla.class.getResource("/recursos/sprites/arquero.png"));
        Consumible vida = new PocionSalud();
        Party partida = new Party();
        Personaje arquero = new Arquero("arquero");
        Personaje guerrero = new Guerrero("guerrero");
        Personaje tanque = new Tanque("tanque");
        Enemigo murcileago = new Murcielago("Muercielago",1);
        Enemigo esqueleto =  new Esqueleto("Skeleton",1);
        partida.agregarPersonaje(arquero);
        partida.agregarPersonaje(guerrero);
        partida.agregarPersonaje(tanque);
        Inventario inventario = partida.getInventario();
        inventario.agregarItem(vida);
        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(murcileago);
        enemigos.add(esqueleto);
        SwingUtilities.invokeLater(() -> {
            PantallaMenu menu = new PantallaMenu();
            menu.setVisible(true);

            Batalla batalla = new Batalla(partida,enemigos);
            PantallaBatalla pantalla = new PantallaBatalla();
            //ControladorBatalla controlador = new ControladorBatalla(batalla,pantalla);
            //pantalla.setControlador(controlador);
            //controlador.refrescarVista();
            pantalla.setVisible(true);
            //controlador.iniciar();
        });
    }
}

