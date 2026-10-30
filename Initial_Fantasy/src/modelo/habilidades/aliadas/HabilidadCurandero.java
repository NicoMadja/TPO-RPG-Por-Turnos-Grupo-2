package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadCurandero extends Habilidad {

    public HabilidadCurandero() {
        super("Sanacion Localizada", "Cura a un aliado el triple del ataque del curandero",20, TipoObjetivo.UN_ALIADO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();


        int cantidadCuracion = lanzador.getAtaqueActual() * 3;
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.recibirCura(cantidadCuracion);
        }
    }
}
