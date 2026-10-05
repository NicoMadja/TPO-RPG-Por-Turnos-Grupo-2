package modelo.entidades.enemigos;

import modelo.habilidades.HabilidadMiniJefe;
import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.acciones.AccionHabilidad;
import modelo.party.Party;
import modelo.entidades.personajes.Personaje;

public class MiniJefe extends Enemigo {

    // --- CONSTRUCTOR ---
    public MiniJefe(String nombre, int nivel) {
        // el minijefe tiene stats muy altas y da el doble/triple de recompensas
        super(nombre, nivel, 150 + (nivel * 20), 40 + (nivel * 5), 22 + (nivel * 5), 15 + (nivel * 4),
                12 + (nivel * 2), new HabilidadMiniJefe(), 100 + (nivel * 30), 50 + (nivel * 10));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // siempre ataca al héroe más débil para intentar matarlo rápido
        Personaje objetivo = heroes.obtenerPersonajeConMenosVida();

        // tiene un 50% de probabilidad de usar su habilidad si tiene maná suficiente
        if (this.manaActual >= 20 && Math.random() > 0.5) {
            return new AccionHabilidad(this, objetivo, this.habilidad);
        }

        // sino hace un golpe básico pero fuerte
        return new AccionAtaque(this, objetivo);
    }
}