package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadGuerrero extends Habilidad {

    public HabilidadGuerrero() {
        super("Golpe Cruzado", 10, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // hace un ataque debilitando la defensa del enemigo a la mitad
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual() - (objetivo.getDefensaActual() / 2);
            if (danioFinal < 0) danioFinal = 0;
            objetivo.recibirDanio(danioFinal);
        }
    }
}
