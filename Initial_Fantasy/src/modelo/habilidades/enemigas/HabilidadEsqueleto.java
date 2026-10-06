package modelo.habilidades.enemigas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class HabilidadEsqueleto extends Habilidad {

    public HabilidadEsqueleto() {
        super("Tajo Rapido", 0);
    }

    @Override
    public void ejecutar(Entidad lanzador, Entidad objetivo) {
        // daña al héroe y aumenta levemente su velocidad y ataque
        int danioFinal = lanzador.getAtaqueActual() - objetivo.getDefensaActual();
        if (danioFinal < 0) {danioFinal = 0;}
        objetivo.recibirDanio(danioFinal);

        lanzador.modificarVelocidad(1);
        lanzador.modificarAtaque(1);
    }
}
