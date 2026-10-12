package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.consumibles.Consumible;
import modelo.party.Inventario;

public class AccionConsumible extends Accion {
    private Inventario inventario;

    public AccionConsumible(ContextoAccion contexto, Inventario inventario) {
        super(contexto);
        this.inventario = inventario;
    }

    @Override
    public void ejecutar() {
        // sacamos el primer objetivo de la lista (el único)
        Entidad objetivo = contexto.getObjetivos().get(0);
        Consumible consumible = contexto.getItem();

        // por si esta muerto, para no darle vida a un muerto
        if (!objetivo.estaVivo()) {
            System.out.println(objetivo.getNombre() + " está derrotado: no se puede usar " + consumible.getNombre() + " sobre él.");
            return;
        }

        // aplicamos el efecto y desccontamos el ítem
        inventario.usarItem(consumible, objetivo);
    }
}
