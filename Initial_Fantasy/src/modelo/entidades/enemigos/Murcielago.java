package modelo.entidades.enemigos;

import modelo.acciones.AccionHabilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.enemigas.HabilidadMurcielago;
import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.party.Party;
import modelo.entidades.personajes.Personaje;

public class Murcielago extends Enemigo {

    // --- CONSTRUCTOR ---
    public Murcielago(String nombre, int nivel) {
        // el murciélago se caracteriza es débil pero muy rápido.
        super(nombre, nivel, 30 + (nivel * 5), 0, 8 + (nivel * 2), 3 + nivel,
                18 + (nivel * 2), new HabilidadMurcielago(), 20 + (nivel * 10), 5 + (nivel * 2));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // ataca a un objetivo aleatorio
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // 75% de posibilidad de ejecutar su habilidad
        if (Math.random() < 0.75) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            // el otro 25% es pegar un ataque basico
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }
}
