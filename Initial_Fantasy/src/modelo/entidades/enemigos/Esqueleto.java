package modelo.entidades.enemigos;

import modelo.habilidades.HabilidadEsqueleto;
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
        // ataca con ataques básicos a un objetivo aleatorio
        Personaje objetivo = heroes.obtenerPersonajeVivoAlAzar();

        // retorna la acción empaquetada para que el Gestor de Turnos la ejecute
        return new AccionAtaque(this, objetivo);
    }
}