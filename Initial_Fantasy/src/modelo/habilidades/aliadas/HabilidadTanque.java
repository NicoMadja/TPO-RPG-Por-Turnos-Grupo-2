package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadTanque extends Habilidad {

    public HabilidadTanque() {
        super("Muro Inquebrantable", 10);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // aumenta el doble la defensa del objetivo
        int aumentoDefensa = lanzador.getDefensaBase() * 2;
        objetivo.modificarDefensa(aumentoDefensa);
    }
}