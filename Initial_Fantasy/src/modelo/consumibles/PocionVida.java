package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionVida extends Consumible {

    public PocionVida() {
        super("Poción de Vida");
    }

    @Override
    public void consumir(Entidad objetivo) {
        // cura 50 puntos de vida
        objetivo.recibirCura(50);
        System.out.println(objetivo.getNombre() + " se curó 50 puntos de vida.");
    }
}