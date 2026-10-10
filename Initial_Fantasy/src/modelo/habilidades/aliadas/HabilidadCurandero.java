package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;

public class HabilidadCurandero extends Habilidad {

    public HabilidadCurandero() {
        super("Sanacion Localizada", 20);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // cura a un aliado el triple del ataque del curandero
        int cantidadCuracion = lanzador.getAtaqueActual() * 3;
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.recibirCura(cantidadCuracion);
        }
    }
}
