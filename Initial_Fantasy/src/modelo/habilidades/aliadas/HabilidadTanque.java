package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;

public class HabilidadTanque extends Habilidad {

    public HabilidadTanque() {
        super("Muro Inquebrantable", 15);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // aumenta la defensa de los objetivos según la defensa base del tanque
        int aumentoDefensa = lanzador.getDefensaBase() * 2;
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.modificarDefensa(aumentoDefensa);
        }
    }
}
