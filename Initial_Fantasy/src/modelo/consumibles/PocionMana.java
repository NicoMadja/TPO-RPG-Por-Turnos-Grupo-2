package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionMana extends Consumible {

    public PocionMana() {
        super("Poción de Maná");
    }

    @Override
    public void consumir(Entidad objetivo) {
        // recupera 50 puntos de mana
        objetivo.recuperarMana(50);
        System.out.println(objetivo.getNombre() + " se recuperó 50 puntos de maná.");
    }
}