package modelo.habilidades.aliadas;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.TipoObjetivo;

public class HabilidadArquero extends Habilidad {

    public HabilidadArquero() {
        super("Flechazo Preciso","Flechazo que hace el doble de daño", 15, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();
        // flechazo que hace el doble de daño
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (!objetivo.estaVivo()) continue;

            int danioFinal = objetivo.calcularDanioRecibido(lanzador.getAtaqueActual() * 2);

            objetivo.recibirDanio(danioFinal);
        }
    }
}
