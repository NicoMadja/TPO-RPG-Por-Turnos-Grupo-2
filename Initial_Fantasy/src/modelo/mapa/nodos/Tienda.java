package modelo.mapa.nodos;

import modelo.consumibles.Consumible;
import modelo.party.Party;
import java.util.List;

public class Tienda extends Nodo {
    private List<Consumible> catalogo;
    private Party party;


    // --- CONSTRUCTOR ---
    public Tienda(List<Consumible> catalogo, Party party) {
        this.catalogo = catalogo;
        this.party= party;
    }


    // --- METODOS ---
    public void comprarObjeto(Consumible c) {
        if (party.gastarOro(c.getPrecio()) && catalogoContieneElItem(c)) {
            party.gastarOro(c.getPrecio());
            party.getInventario().agregarItem(c);
            System.out.println("Se compró " + c.getNombre() + " con éxito.");
            catalogo.remove(c);
        }
    }

    private boolean catalogoContieneElItem(Consumible c) {
        return catalogo.contains(c);
    }


    // --- GETTERS ---
    public List<Consumible> getCatalogo() {return catalogo;}


    // --- PROCESAR ---
    @Override
    public void procesarNodo() {

    }
}
