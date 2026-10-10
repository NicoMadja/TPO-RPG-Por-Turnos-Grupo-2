package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;

public class HabilidadBrujo extends Habilidad {

    public HabilidadBrujo() {
        super("Rayo Oscuro", 30);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        // resta 8 de ataque y 8 de defensa al héroe
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            objetivo.modificarAtaque(-8);
            objetivo.modificarDefensa(-8);
        }
    }
}