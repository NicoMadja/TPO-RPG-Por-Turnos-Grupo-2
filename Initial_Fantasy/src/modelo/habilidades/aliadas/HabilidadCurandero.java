package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadCurandero extends Habilidad {

    public HabilidadCurandero() {
        super("Sanacion Localizada", 20);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // cura a un aliado el triple del daño del curandero
        int cantidadCuracion = lanzador.getAtaqueActual() * 3;

        objetivo.recibirCura(cantidadCuracion);
    }
}
