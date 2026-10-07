package modelo.acciones;

import modelo.entidades.Entidad;

public class AccionDefensa extends Accion {

    public AccionDefensa(ContextoAccion contexto) {
        super(contexto);
    }

    @Override
    public void ejecutar() {
        Entidad defensor = contexto.getOrigen();

        // postura defensiva
        defensor.setDefendiendo(true);
        System.out.println(defensor.getNombre() + " adopta una postura defensiva.");
    }
}
