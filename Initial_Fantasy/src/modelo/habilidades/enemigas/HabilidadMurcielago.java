package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadMurcielago extends Habilidad {

    public HabilidadMurcielago() {
        super("Mordida", "Daña ignorando la defensa del héroe y se cura lo que quitó",0, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();
        // daña ignorando la defensa del héroe y se cura lo que quitó
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual();

            objetivo.recibirDanio(danioFinal);
            lanzador.recibirCura(danioFinal);
        }
    }
}
