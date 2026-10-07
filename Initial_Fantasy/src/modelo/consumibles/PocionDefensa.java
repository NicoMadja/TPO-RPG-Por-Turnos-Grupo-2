package modelo.consumibles;

import modelo.entidades.Entidad;

public class PocionDefensa extends Consumible {

    public PocionDefensa() {
        super("Poción de Defensa");
    }

    @Override
    public void consumir(Entidad objetivo) {
        // duplica la defensa hasta el final de la batalla
        int aumentoDefensa = objetivo.getDefensaActual();
        objetivo.modificarDefensa(aumentoDefensa);
        System.out.println(objetivo.getNombre() + " se duplicó la defensa pasiva.");
    }
}