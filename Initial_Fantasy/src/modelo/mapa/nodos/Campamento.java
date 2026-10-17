package modelo.mapa.nodos;

import modelo.entidades.personajes.Personaje;
import modelo.party.Party;

public class Campamento extends Nodo {
    private Party party;

    // --- CONSTRUCTOR ---
    public Campamento() {
    }


    // --- METODOS ---
    public void descansar() {
        for (Personaje p: party.getPersonajes()) {
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
