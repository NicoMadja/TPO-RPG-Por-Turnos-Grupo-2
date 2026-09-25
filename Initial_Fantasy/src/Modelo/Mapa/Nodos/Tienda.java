package Modelo.Mapa.Nodos;

import Modelo.Consumibles.Consumible;

import java.util.List;

public class Tienda {
    private List<Consumible> catalogo;

    // oor ahi es mejor consultarle directamente al consumible su precio
    public int consultarPrecio(Consumible item) {
        return item.getPrecio();
    }

    public void comprarObjeto(Consumible item, int oro) {
        // borrar objeto de catalogo
        // restar oro a monedero de Party
        // agregar objeto a Pnventario de Party
    }
}
