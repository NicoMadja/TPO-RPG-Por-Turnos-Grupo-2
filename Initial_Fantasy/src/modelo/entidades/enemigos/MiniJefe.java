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
        // el minijefe es un enemigo mas avanzado y fuerte que el resto
        super(nombre, nivel, 180 + (nivel * 5), 100, 22 + (nivel * 2), 8 + nivel,
                11 + (nivel * 2), new HabilidadMiniJefe(), "/recursos/sprites/minijefe.png",
                160 + (nivel * 10), 120 + (nivel * 2));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // ataca objetivo aleatorio
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // 40% de usar su habilidad si tiene maná suficiente
        if (tieneSuficienteMana(this.habilidad.getCostoMana()) && Math.random() < 0.40) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }
}
