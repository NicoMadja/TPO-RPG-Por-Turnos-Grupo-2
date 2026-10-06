package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadMago extends Habilidad {

    public HabilidadMago() {
        super("Bola de Fuego", 35);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // daño masivo a todos los enemigos, ignorando defensa
        int danioMagico = lanzador.getAtaqueActual() * 2;
        objetivo.recibirDanio(danioMagico);
    }
}