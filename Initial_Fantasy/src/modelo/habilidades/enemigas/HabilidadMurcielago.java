package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadMurcielago extends Habilidad {

    public HabilidadMurcielago() {
        super("Mordida", 0);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // daña poco ignorando la defensa del héro y se cura a sí mismo
        int danioFinal = lanzador.getAtaqueActual();
        objetivo.recibirDanio(danioFinal);

        // se cura la misma cantidad de vida que quitó
        lanzador.recibirCura(danioFinal);
    }
}
