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
        super(nombre, nivel, 35 + (nivel * 5), 0, 12 + (nivel * 2), 3 + nivel,
                12, new HabilidadMurcielago(), "/recursos/sprites/murcielago.png",
                25 + (nivel * 10), 15 + (nivel * 2));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // ataca a un objetivo aleatorio
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // 100% de posibilidad de ejecutar su habilidad (ataca siempre con su habilidad)
        ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
        return new AccionHabilidad(contextoHabilidad);
    }
}
