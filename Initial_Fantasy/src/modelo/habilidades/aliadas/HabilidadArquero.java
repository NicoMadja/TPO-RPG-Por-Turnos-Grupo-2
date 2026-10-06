package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadArquero extends Habilidad {

    public HabilidadArquero() {
        super("Flechazo Preciso", 15);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // tira un flechazo que baja la velocidad y defensa del enemigo
        int danioFinal = lanzador.getAtaqueActual() - objetivo.getDefensaActual();
        if (danioFinal < 0) {danioFinal = 0;}

        // aplicamos el daño y los debuffs
        objetivo.recibirDanio(danioFinal);
        objetivo.modificarVelocidad(-1);
        objetivo.modificarDefensa(-5);
    }
}
