package modelo.entidades.enemigos;

import modelo.habilidades.HabilidadBrujo;
import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.acciones.AccionHabilidad;
import modelo.party.Party;
import modelo.entidades.personajes.Personaje;

public class Brujo extends Enemigo {

    // --- CONSTRUCTOR ---
    public Brujo(String nombre, int nivel) {
        // el brujo tiene muy poca vida y defensa física, pero es rápido y usa maná para su habilidad
        super(nombre, nivel, 40 + (nivel * 5), 50 + (nivel * 10), 6 + nivel, 4 + nivel,
                14 + (nivel * 2), new HabilidadBrujo(), 35 + (nivel * 12), 12 + (nivel * 4));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // si tiene suficiente maná, prioriza usar la habilidad
        if (this.manaActual >= 15) {
            return new AccionHabilidad(this, objetivo, this.habilidad);
        }

        // si se quedó sin maná, se ve obligado a hacer un ataque físico débil
        return new AccionAtaque(this, objetivo);
    }
}