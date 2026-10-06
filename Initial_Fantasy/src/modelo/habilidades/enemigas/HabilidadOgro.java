package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadOgro extends Habilidad {

    public HabilidadOgro() {
        super("Golpe Enfurecido", 0);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // aumenta su ataque y defensa en 2 hasta que termine la batalla y hace un ataque con el doble de su daño
        lanzador.modificarAtaque(2);
        lanzador.modificarDefensa(2);

        int danioFinal = lanzador.getAtaqueActual() * 2 - objetivo.getDefensaActual();
        if (danioFinal < 0) {danioFinal = 0;}
        objetivo.recibirDanio(danioFinal);
    }
}