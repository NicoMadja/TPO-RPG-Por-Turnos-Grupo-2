package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadCurandero extends Habilidad {

    public HabilidadCurandero() {
        super("Sanacion Localizada", "Cura un 50% de vida de un aliado", 20, TipoObjetivo.UN_ALIADO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        // cura un 50% de vida de un aliado
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) {

                int cantidadCuracion = objetivo.getVidaMaxima() / 2;

                objetivo.recibirCura(cantidadCuracion);
            }
        }
    }
}
