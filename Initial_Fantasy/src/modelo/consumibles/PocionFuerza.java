package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionFuerza extends Consumible {

    public PocionFuerza() {
        super("Poción de Fuerza", 600);
    }

    @Override
    public void consumir(Entidad objetivo) {
        // duplica el daño hasta el final de la batalla
        int aumentoAtaque = objetivo.getAtaqueActual();
        objetivo.modificarAtaque(aumentoAtaque);
        System.out.println(objetivo.getNombre() + " se duplicó el ataque.");
    }
}