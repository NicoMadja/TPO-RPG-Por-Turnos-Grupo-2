package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadMago extends Habilidad {

    public HabilidadMago() {
        super("Bola de Fuego", "Daña a todos los objetivos del contexto, ignorando defensa", 35, TipoObjetivo.TODOS_LOS_ENEMIGOS);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();
        // daña a todos los objetivos del contexto, ignorando defensa
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) {

                int danioMagico = lanzador.getAtaqueActual();

                objetivo.recibirDanio(danioMagico);

            }
        }
    }
}
