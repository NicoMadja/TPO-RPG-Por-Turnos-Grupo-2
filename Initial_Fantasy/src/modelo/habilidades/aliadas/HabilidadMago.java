package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;

public class HabilidadMago extends Habilidad {

    public HabilidadMago() {
        super("Bola de Fuego", 35);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // daño masivo a todos los objetivos del contexto, ignorando defensa
        int danioMagico = lanzador.getAtaqueActual() * 2;
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.recibirDanio(danioMagico);
        }
    }
}
