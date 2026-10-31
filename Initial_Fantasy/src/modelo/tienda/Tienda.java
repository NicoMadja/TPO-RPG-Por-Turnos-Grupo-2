package modelo.tienda;

import modelo.consumibles.Consumible;
import modelo.mundo.EscenarioDeNodos;
import modelo.mundo.Nodo;
import modelo.party.Party;

import java.io.Serializable;
import java.util.List;

public class Tienda extends Nodo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Consumible> catalogo;
    private Party party;


    // --- CONSTRUCTOR ---
    public Tienda(List<Consumible> catalogo, Party party) {
        this.catalogo = catalogo;
        this.party= party;
    }


    // --- METODOS ---
    public boolean comprarObjeto(Consumible c) {
        // primero se verifica que el ítem esté en venta y después se cobra
        if (catalogoContieneElItem(c) && party.gastarOro(c.getPrecio())) {
            party.getInventario().agregarItem(c);
            catalogo.remove(c);
            return true;
        }
        return false;
        // boolean para saber si se compró con éxito
    }

    private boolean catalogoContieneElItem(Consumible c) {
        return catalogo.contains(c);
    }


    // --- PROCESAR ---
    @Override
    public void procesarNodo(EscenarioDeNodos escenario) {
        escenario.alEntrarEnTienda(this);
    }


    // --- GETTERS ---
    public List<Consumible> getCatalogo() {return catalogo;}
    public Party getParty() {return party;}
}
