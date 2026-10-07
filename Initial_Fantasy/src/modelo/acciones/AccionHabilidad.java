package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class AccionHabilidad extends Accion {

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();
        Habilidad habilidadUsada = contexto.getHabilidad();

        if (lanzador.getManaActual() >= habilidadUsada.getCostoMana()) {
            lanzador.gastarMana(habilidadUsada.getCostoMana());

            for (Entidad objetivo : contexto.getObjetivos()) {
                if (objetivo.estaVivo()) habilidadUsada.ejecutar(lanzador, objetivo);
            }

            System.out.println(lanzador.getNombre() + " usó " + habilidadUsada.getNombre());

        } else {
            System.out.println(lanzador.getNombre() + " no tiene maná suficiente.");
        }
    }
}