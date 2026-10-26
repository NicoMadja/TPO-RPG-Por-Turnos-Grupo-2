package modelo.tienda;

import modelo.consumibles.Consumible;
import modelo.mundo.EscenarioDeNodos;
import modelo.mundo.Nodo;
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
        // primero se verifica que el ítem esté en venta y después se cobra
        if (catalogoContieneElItem(c) && party.gastarOro(c.getPrecio())) {
            // se agrega al inventario
            party.getInventario().agregarItem(c);
            System.out.println("Se compró " + c.getNombre() + " con éxito.");
            // se saca del catalogo
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
    public void procesarNodo(EscenarioDeNodos escenario) {
        escenario.alEntrarEnTienda(this);
    }

    public Party getParty() {
        return party;
    }
}
