package modelo.entidades.enemigos;

import modelo.acciones.AccionHabilidad;
import modelo.acciones.ContextoAccion;
import modelo.habilidades.enemigas.HabilidadEsqueleto;
import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.party.Party;
import modelo.entidades.personajes.Personaje;

public class Esqueleto extends Enemigo {

    // --- CONSTRUCTOR ---
    public Esqueleto(String nombre, int nivel) {
        // el esqueleto es un enemigo básico y balanceado. estadísticas intermedias
        super(nombre, nivel, 50 + (nivel * 8), 0, 12 + (nivel * 3), 5 + (nivel * 2),
                10 + nivel, new HabilidadEsqueleto(), 25 + (nivel * 10), 10 + (nivel * 3));
    }

    // --- COMPORTAMIENTO ---
    @Override
    public Accion decidirAccion(Party heroes) {
        // ataca objetivo aleatorio
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // 50% de posibilidad de ejecutar su habilidad
        if (Math.random() < 0.5) {
            ContextoAccion contextoHabilidad = new ContextoAccion(this, objetivo, null, this.habilidad);
            return new AccionHabilidad(contextoHabilidad);
        } else {
            // el otro 50% es pegar un ataque basico
            ContextoAccion contexto = new ContextoAccion(this, objetivo, null, null);
            return new AccionAtaque(contexto);
        }
    }
}
