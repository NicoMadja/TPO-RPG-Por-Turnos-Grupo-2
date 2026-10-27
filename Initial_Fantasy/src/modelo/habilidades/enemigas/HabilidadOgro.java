package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadOgro extends Habilidad {

    public HabilidadOgro() {
        super("Golpe Enfurecido", 0, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // aumenta un 15% su ataque hasta que termine la batalla (una sola vez por uso)
        int buffAtaque = (lanzador.getAtaqueActual() * 115) / 100;
        lanzador.modificarAtaque(buffAtaque);

        // y hace un ataque con 30% mas de daño
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = ((lanzador.getAtaqueActual() * 130) / 100) - objetivo.getDefensaActual();
            if (danioFinal < 0) danioFinal = 0;
            objetivo.recibirDanio(danioFinal);
        }
    }
}