package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadArquero extends Habilidad {

    public HabilidadArquero() {
        super("Flechazo Preciso","Flechazo que baja la velocidad y la defensa del enemigo", 15, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // flechazo que hace el doble de daño
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = (lanzador.getAtaqueActual() * 2) - objetivo.getDefensaActual();
            if (danioFinal < 0) danioFinal = 0;
            objetivo.recibirDanio(danioFinal);
        }
    }
}
