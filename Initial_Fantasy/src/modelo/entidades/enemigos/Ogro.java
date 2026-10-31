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
        // el ogro tiene muchísima vida y daño, pero es muy lento y sin maná
        super(nombre, nivel, 110 + (nivel * 15), 0, 20 + (nivel * 4), 10 + (nivel * 3),
                7, new HabilidadOgro(), "/recursos/sprites/ogro.png",
                80 + (nivel * 15), 60 + (nivel * 5));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // aplasta al personaje que tenga menos vida
        Personaje objetivo = heroes.obtenerPersonajeConMenosVida();

        // 30% de posibilidad de ejecutar su habilidad
        if (Math.random() < 0.30) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            // el otro 70% es pegar un ataque basico
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }
}
