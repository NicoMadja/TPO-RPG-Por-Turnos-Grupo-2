package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionMana extends Consumible {

    public PocionMana() {
        super("Poción de Maná", 25, "/recursos/sprites/pocionmana.png");
    }

    @Override
    public void consumir(Entidad objetivo) {
        // recupera todo el mana
        objetivo.recuperarMana(objetivo.getManaMaximo());
        System.out.println(objetivo.getNombre() + " tomó una poción y recuperó todo su maná.");
    }
}