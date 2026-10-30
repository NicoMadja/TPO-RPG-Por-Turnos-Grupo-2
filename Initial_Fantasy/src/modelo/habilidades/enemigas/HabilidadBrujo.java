package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadBrujo extends Habilidad {

    public HabilidadBrujo() {
        super("Rayo Oscuro", "Resta 20% la defensa de un héroe hasta el final de la batalla", 30, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        // resta 20% la defensa de un héroe hasta el final de la batalla
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int debuffDefensa = -((objetivo.getDefensaActual() * 20) / 100);

            objetivo.modificarDefensa(debuffDefensa);
        }
    }
}