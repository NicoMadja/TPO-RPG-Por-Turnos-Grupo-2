package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadTanque extends Habilidad {

    public HabilidadTanque() {
        super("Muro Inquebrantable", "Aumenta un 30% la defensa de todos los aliados", 15, TipoObjetivo.TODOS_LOS_ALIADOS);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();


        for (Entidad objetivo : contexto.getObjetivos()) {
            int aumentoDefensa = (objetivo.getDefensaActual() * 130) / 100;
            if (objetivo.estaVivo()) objetivo.modificarDefensa(aumentoDefensa);
        }
    }
}
