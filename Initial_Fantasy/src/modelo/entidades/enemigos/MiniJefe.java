package modelo.entidades.enemigos;

import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.acciones.AccionHabilidad;
import modelo.acciones.ContextoAccion;
import modelo.entidades.personajes.Personaje;
import modelo.habilidades.enemigas.HabilidadMiniJefe;
import modelo.party.Party;

public class MiniJefe extends Enemigo {

    public MiniJefe(String nombre, int nivel) {
        super(nombre, nivel, 70 + (nivel * 5), 100, 20 + (nivel * 2), 5 + nivel, 9 + (nivel * 2), new HabilidadMiniJefe(), "/recursos/sprites/minijefe.png", 45 + (nivel * 10), 16 + (nivel * 2));
    }



    @Override
    public Accion decidirAccion(Party heroes) {

        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        if (Math.random() < 0.30) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }


}
