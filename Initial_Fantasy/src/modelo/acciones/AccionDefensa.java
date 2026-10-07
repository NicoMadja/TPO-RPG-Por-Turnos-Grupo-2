package modelo.acciones;

import modelo.entidades.Entidad;

public class AccionDefensa extends Accion {

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad defensor = contexto.getOrigen();

        // postura defensiva
        defensor.setDefendiendo(true);
        System.out.println(defensor.getNombre() + " adopta una postura defensiva.");
    }
}
