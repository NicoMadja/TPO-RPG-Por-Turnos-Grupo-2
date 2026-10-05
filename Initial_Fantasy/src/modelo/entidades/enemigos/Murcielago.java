package modelo.entidades.enemigos;

import modelo.habilidades.HabilidadMurcielago;
import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.party.Party;
import modelo.entidades.personajes.Personaje;

public class Murcielago extends Enemigo {

    // --- CONSTRUCTOR ---
    public Murcielago(String nombre, int nivel) {
        // el murciélago se caracteriza es débil pero muy rápido.
        super(nombre, nivel, 30 + (nivel * 5), 0,8 + (nivel * 2), 3 + nivel,
                18 + (nivel * 2), new HabilidadMurcielago(), 20 + (nivel * 10), 5 + (nivel * 2));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // ataca con ataques básicos a un objetivo aleatorio.
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // retorna la acción empaquetada para que el Gestor de Turnos la ejecute
        return new AccionAtaque(this, objetivo);
    }
}
