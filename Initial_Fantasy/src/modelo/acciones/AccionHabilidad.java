package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public class AccionHabilidad extends Accion {

    public AccionHabilidad(ContextoAccion contexto) {
        super(contexto);
    }

    @Override
    public void ejecutar() {
        Entidad lanzador = contexto.getOrigen();
        Habilidad habilidadUsada = contexto.getHabilidad();

        // si tiene suficiente maná, se resta al lanzador y la habilidad se encarga del resto
        if (lanzador.tieneSuficienteMana(habilidadUsada.getCostoMana())) {
            lanzador.gastarMana(habilidadUsada.getCostoMana());
            habilidadUsada.ejecutar(contexto);

            System.out.println(lanzador.getNombre() + " usó " + habilidadUsada.getNombre());

        } else {
            System.out.println(lanzador.getNombre() + " no tiene maná suficiente.");
        }
    }
}