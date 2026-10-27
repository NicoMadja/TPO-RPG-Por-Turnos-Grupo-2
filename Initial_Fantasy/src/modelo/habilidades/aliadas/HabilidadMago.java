package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadMago extends Habilidad {

    public HabilidadMago() {
        super("Bola de Fuego", 35, TipoObjetivo.TODOS_LOS_ENEMIGOS);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // ataca con el doble de daño a todos los objetivos del contexto
        int danioMagico = lanzador.getAtaqueActual();
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.recibirDanio(danioMagico);
        }
    }
}
