package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadTanque extends Habilidad {

    public HabilidadTanque() {
        super("Muro Inquebrantable", "Aumenta la defensa de los objetivos según la defensa base del tanque", 15, TipoObjetivo.TODOS_LOS_ALIADOS);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();


        int aumentoDefensa = lanzador.getDefensaBase() * 2;
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.modificarDefensa(aumentoDefensa);
        }
    }
}
