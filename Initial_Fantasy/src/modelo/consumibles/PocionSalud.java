package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionSalud extends Consumible {

    public PocionSalud() {
        super("Poción de Vida", 30, "/recursos/sprites/pocionsalud.png");
    }

    @Override
    public void consumir(Entidad objetivo) {

       // cura toda la vida
        objetivo.recibirCura(objetivo.getVidaMaxima());
        System.out.println(objetivo.getNombre() + " tomó una poción y recuperó toda su vida.");
    }
}