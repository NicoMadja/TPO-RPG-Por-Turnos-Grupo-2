package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.consumibles.Consumible;

public class AccionConsumible extends Accion {

    @Override
    public void ejecutar(ContextoAccion contexto) {
        // sacamos el primer objetivo de la lista (el único)
        Entidad objetivo = contexto.getObjetivos().get(0);

        Consumible pocion = contexto.getItem();

        // consumimos la pocion
        if (pocion != null) {
            pocion.consumir(objetivo);
        }
    }
}