package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;

public class HabilidadGuerrero extends Habilidad {

    public HabilidadGuerrero() {
        super("Golpe Cruzado", 10);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // hace el doble de daño y la defensa del enemigo bloquea menos
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual() * 2 - objetivo.getDefensaActual() / 2;
            if (danioFinal < 0) danioFinal = 0;
            objetivo.recibirDanio(danioFinal);
        }
    }
}
