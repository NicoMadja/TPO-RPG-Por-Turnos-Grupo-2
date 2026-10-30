package modelo.entidades.enemigos;

import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.acciones.AccionHabilidad;
import modelo.acciones.ContextoAccion;
import modelo.entidades.personajes.Personaje;
import modelo.habilidades.enemigas.HabilidadMiniJefe;
import modelo.party.Party;

public class MiniJefe extends Enemigo {

    // --- CONSTRUCTOR ---
    public MiniJefe(String nombre, int nivel) {
        super(nombre, nivel, 180 + (nivel * 5), 50, 22 + (nivel * 2), 8 + nivel,
                11 + (nivel * 2), new HabilidadMiniJefe(), "/recursos/sprites/minijefe.png",
                160 + (nivel * 10), 120 + (nivel * 2));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // ataca objetivo aleatorio
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // si tiene 50% de vida o menos y tiene suficiente maná, usa su habilidad
        if (((this.getVidaActual() * 2) <= this.getVidaMaxima()) && tieneSuficienteMana(this.habilidad.getCostoMana())) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }
}
