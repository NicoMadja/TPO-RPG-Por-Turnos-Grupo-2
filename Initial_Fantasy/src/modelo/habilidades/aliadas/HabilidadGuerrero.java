package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadGuerrero extends Habilidad {

    public HabilidadGuerrero() {
        super("Golpe Cruzado", "Ataca ignorando la defensa del enemigo", 10, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();
        // ataca ignorando la defensa del enemigo
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual();

            objetivo.recibirDanio(danioFinal);
        }
    }
}
