package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadGuerrero extends Habilidad {

    public HabilidadGuerrero() {
        super("Golpe Cruzado", 10);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // hace el doble de daño y la defensa del enemigo bloquea menos
        int danioFinal = (lanzador.getAtaqueActual() * 2 - objetivo.getDefensaActual() / 2);
        if (danioFinal < 0) {danioFinal = 0;}
        // aplicamos el daño
        objetivo.recibirDanio(danioFinal);
    }
}
