package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadOgro extends Habilidad {

    public HabilidadOgro() {
        super("Golpe Enfurecido", "aumenta su ataque y defensa en 2 hasta que termine la batalla",0, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();


        lanzador.modificarAtaque(2);
        lanzador.modificarDefensa(2);

        // y hace un ataque con el doble de su daño
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = lanzador.getAtaqueActual() * 2 - objetivo.getDefensaActual();
            if (danioFinal < 0) danioFinal = 0;
            objetivo.recibirDanio(danioFinal);
        }
    }
}