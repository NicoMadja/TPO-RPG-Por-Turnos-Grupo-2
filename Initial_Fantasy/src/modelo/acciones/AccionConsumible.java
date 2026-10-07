package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.consumibles.Consumible;

public class AccionConsumible extends Accion {

    public AccionConsumible (ContextoAccion contexto) {
        super(contexto);
    }

    @Override
    public void ejecutar() {
        // sacamos el primer objetivo de la lista (el único)
        Entidad objetivo = contexto.getObjetivos().get(0);
        Consumible pocion = contexto.getItem();

        // consumimos la pocion
        if (pocion != null) {
            pocion.consumir(objetivo);
        }
    }
}