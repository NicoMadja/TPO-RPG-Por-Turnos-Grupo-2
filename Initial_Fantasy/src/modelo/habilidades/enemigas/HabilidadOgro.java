package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadOgro extends Habilidad {

    public HabilidadOgro() {
        super("Golpe Enfurecido", "Aumenta un 10% su ataque y ataca",0, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();
        // aumenta un 10% su ataque hasta que termine la batalla
        int buffAtaque = (lanzador.getAtaqueActual() * 10) / 100;

        lanzador.modificarAtaque(buffAtaque);

        // y ataca normalmente
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = objetivo.calcularDanioRecibido(lanzador.getAtaqueActual());

            objetivo.recibirDanio(danioFinal);
        }
    }
}