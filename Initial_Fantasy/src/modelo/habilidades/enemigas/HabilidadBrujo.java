package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadBrujo extends Habilidad {

    public HabilidadBrujo() {
        super("Rayo Oscuro", 30, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        // resta 20% de defensa actual a uno de los héroes hasta el final de la batalla
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int debuffDefensa = -((objetivo.getDefensaActual() * 20) / 100);
            objetivo.modificarDefensa(debuffDefensa);
        }
    }
}