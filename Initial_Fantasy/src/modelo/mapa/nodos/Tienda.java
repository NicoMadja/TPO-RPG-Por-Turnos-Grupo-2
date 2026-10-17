package modelo.mapa.nodos;

import modelo.consumibles.Consumible;
import modelo.party.Inventario;
import modelo.party.Party;
import java.util.List;

public class Tienda extends Nodo {
    private List<Consumible> catalogo;
    private Party party;
    private Inventario inventario;


    // --- CONSTRUCTOR ---
    public Tienda(List<Consumible> catalogo) {
        this.catalogo = catalogo;
    }


    // --- METODOS ---
    public void comprarObjeto(Consumible c) {
        if (party.puedeGastarOro(c.getPrecio())) {
            party.gastarOro(c.getPrecio());
            inventario.agregarItem(c);
            catalogo.remove(c);
            System.out.println("Se compró " + c.getNombre() + " con éxito.");
        }
    }


    // --- GETTERS ---
    public List<Consumible> getCatalogo() {return catalogo;}


    // --- PROCESAR ---
    @Override
    public void procesarNodo() {

    }
}
