package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadBrujo extends Habilidad {

    public HabilidadBrujo() {
        super("Rayo Oscuro", 30);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // resta 8 de ataque y 8 de defensa al héroe,
        objetivo.modificarAtaque(-8);
        objetivo.modificarDefensa(-8);
    }
}