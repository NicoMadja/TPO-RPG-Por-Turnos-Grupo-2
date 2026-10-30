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
        // aumenta un 30% la defensa de todos los aliados
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) {

                int aumentoDefensa = (objetivo.getDefensaActual() * 30) / 100;

                objetivo.modificarDefensa(aumentoDefensa);
            }
        }
    }
}
