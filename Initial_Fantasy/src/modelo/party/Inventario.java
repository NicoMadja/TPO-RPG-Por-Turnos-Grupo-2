package modelo.party;

import modelo.consumibles.Consumible;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Consumible> items;


    // --- CONSTRUCTOR ---
    public Inventario() {
        this.items = new ArrayList<>();
    }


    // --- GESTION ---
    public void agregarItem(Consumible item) {
        this.items.add(item);
    }

    public void usarItem(Consumible item, modelo.entidades.Entidad objetivo) {
        if (this.items.contains(item)) {
            item.consumir(objetivo); // efecto de la poción
            this.items.remove(item); // lo borramos
        }
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }


    // --- GETTERS ---
    public List<Consumible> getItems() {return items;}
}
