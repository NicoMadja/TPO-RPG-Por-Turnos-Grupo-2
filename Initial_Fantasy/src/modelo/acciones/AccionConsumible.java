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
        if (contexto.getObjetivos().isEmpty()) return;
        Entidad objetivo = contexto.getObjetivos().get(0);
        Consumible consumible = contexto.getItem();

        // consumimos la consumible
        if (consumible != null) {
            consumible.consumir(objetivo);
        }
    }
}