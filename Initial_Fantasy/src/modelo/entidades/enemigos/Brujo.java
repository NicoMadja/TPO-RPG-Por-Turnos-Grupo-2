package modelo.entidades.enemigos;

import modelo.acciones.ContextoAccion;
import modelo.habilidades.enemigas.HabilidadBrujo;
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
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        }

        // si se quedó sin maná, se ve obligado a hacer un ataque físico débil
        ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
        return new AccionAtaque(contexto);
    }
}
