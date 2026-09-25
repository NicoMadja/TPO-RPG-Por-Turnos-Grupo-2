package Modelo.Party;

import Modelo.Consumibles.Consumible;
import java.util.Map;

public class Inventario {
    private Map<Consumible, Integer> items;

                                               //cantidad?
    public void agregarItem(Consumible item, int cantidad) {
        items.merge(item, cantidad, Integer::sum);
    }

    public void consumirItem(Consumible item) {
        // restar 1 de cantidad
    }

    public boolean tieneItem(Consumible item) {
        return items.containsKey(item);
    }
}
