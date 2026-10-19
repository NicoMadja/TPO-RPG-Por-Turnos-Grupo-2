package modelo.mapa.nodos;

import modelo.entidades.personajes.Personaje;
import modelo.party.Party;

public class Campamento extends Nodo {
    private Party party;

    // --- CONSTRUCTOR ---
    public Campamento(Party party) {
        this.party = party;
    }


    // --- METODOS ---
    public void descansar() {
        for (Personaje p: party.getPersonajes()) {
            // revive, cura y recupera maná a todos los aliados
            p.recibirCura(p.getVidaMaxima() - p.getVidaActual());   // recuperar vida hasta el maximo
            p.recuperarMana(p.getManaMaximo() - p.getManaActual()); // recuperar maná hasta el maximo
        }
    }


    // --- PROCESAR ---
    @Override
    public void procesarNodo() {
        descansar(); // ???
    }
}
