package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadBrujo extends Habilidad {

    public HabilidadBrujo() {
        super("Rayo Oscuro", "Resta 8 de ataque y 8 de defensa al héroe",30, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {

        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            objetivo.modificarAtaque(-8);
            objetivo.modificarDefensa(-8);
        }
    }
}