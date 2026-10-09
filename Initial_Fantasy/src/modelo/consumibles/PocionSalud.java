package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionSalud extends Consumible {

    public PocionSalud() {
        super("Poción de Vida", 550);
    }

    @Override
    public void consumir(Entidad objetivo) {
        // cura 50 puntos de vida
        objetivo.recibirCura(50);
        System.out.println(objetivo.getNombre() + " se curó 50 puntos de vida.");
    }
}