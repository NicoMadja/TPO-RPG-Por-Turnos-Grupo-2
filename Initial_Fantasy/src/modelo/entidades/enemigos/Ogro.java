package modelo.entidades.enemigos;

import modelo.acciones.AccionHabilidad;
import modelo.acciones.ContextoAccion;
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
        // aplasta al personaje que tenga menos vida
        Personaje objetivo = heroes.obtenerPersonajeConMenosVida();

        // 25% de posibilidad de ejecutar su habilidad
        if (Math.random() < 0.25) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            // el otro 75% es pegar un ataque basico
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }
}