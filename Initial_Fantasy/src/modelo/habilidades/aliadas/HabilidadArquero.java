package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;

public class HabilidadArquero extends Habilidad {

    public HabilidadArquero() {
        super("Flechazo Preciso", 15);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // flechazo que baja la velocidad y la defensa del enemigo
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual() - objetivo.getDefensaActual();
            if (danioFinal < 0) danioFinal = 0;

            objetivo.recibirDanio(danioFinal);
            objetivo.modificarVelocidad(-1);
            objetivo.modificarDefensa(-5);
        }
    }
}
