package modelo.entidades.enemigos;

import modelo.habilidades.enemigas.HabilidadOgro;
import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.party.Party;
import modelo.entidades.personajes.Personaje;

public class Ogro extends Enemigo {

    // --- CONSTRUCTOR ---
    public Ogro(String nombre, int nivel) {
        // el ogro se caracteriza por tener muchísima vida y daño, pero es muy lento y sin maná
        super(nombre, nivel, 100 + (nivel * 15), 0, 18 + (nivel * 4), 8 + (nivel * 3),
                5 + nivel, new HabilidadOgro(), 40 + (nivel * 15), 15 + (nivel * 5));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // el ogro aplasta al personaje que tenga menos vida
        Personaje objetivo = heroes.obtenerPersonajeConMenosVida();

        // retorna la acción empaquetada para que el Gestor de Turnos la ejecute
        return new AccionAtaque(this, objetivo);
    }
}