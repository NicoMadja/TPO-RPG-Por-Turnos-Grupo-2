package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadEsqueleto extends Habilidad {

    public HabilidadEsqueleto() {
        super("Tajo Óseo", 0, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // daña al héroe y aumenta levemente su velocidad
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual() - objetivo.getDefensaActual();
            if (danioFinal < 0) danioFinal = 0;
            objetivo.recibirDanio(danioFinal);

            lanzador.modificarVelocidad(1);
        }
    }
}
