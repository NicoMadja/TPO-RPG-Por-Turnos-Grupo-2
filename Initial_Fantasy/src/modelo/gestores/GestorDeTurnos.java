package modelo.gestores;

import modelo.entidades.Entidad;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.personajes.Personaje;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class GestorDeTurnos {
    private List<Personaje> personajes;
    private List<Enemigo> enemigos;
    private Queue<Entidad> turnosDeLaRonda;
    private Entidad entidadActual;


    // --- CONSTRUCTOR ---
    public GestorDeTurnos() {
        this.personajes = new ArrayList<>();
        this.enemigos = new ArrayList<>();
        this.turnosDeLaRonda = new ArrayDeque<>();
    }


    // --- PREPARACION ---
    public void enlistarEntidades(List<Personaje> personajes, List<Enemigo> enemigos) {
        this.personajes = personajes;
        this.enemigos = enemigos;
        this.turnosDeLaRonda.clear();
        avanzarTurno(); // deja lista a la primera entidad
    }


    // --- FLUJO DE TURNOS ---
    public Entidad obtenerEntidadActual() {
        return entidadActual;
    }

    // solo se llama si la batalla no terminó (verificarFinBatalla() == false)
    public void avanzarTurno() {
        do {
            if (turnosDeLaRonda.isEmpty()) nuevaRonda();
            entidadActual = turnosDeLaRonda.poll();
            // si alguien murió antes de actuar en esta ronda, se saltea su turno
        } while (!entidadActual.estaVivo());

        entidadActual.iniciarTurno();
    }

    private void nuevaRonda() {
        // en caso de empate de velocidad, actuan primero los héroes
        List<Entidad> vivos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos.add(p);
        }
        for (Enemigo e : enemigos) {
            if (e.estaVivo()) vivos.add(e);
        }

        // se recalcula en cada ronda porque la velocidad puede cambiar en combate
        vivos.sort(Comparator.comparingInt(Entidad::getVelocidadActual).reversed());
        turnosDeLaRonda.addAll(vivos);
    }


    // --- CONDICIONES FINALES DE BATALLA ---
    public boolean personajesDerrotados() {
        for (Personaje p : personajes) {
            if (p.estaVivo()) return false;
        }
        return true;
    }

    public boolean enemigosDerrotados() {
        for (Enemigo e : enemigos) {
            if (e.estaVivo()) return false;
        }
        return true;
    }

    public boolean verificarFinBatalla() {
        return personajesDerrotados() || enemigosDerrotados();
    }

    public List<String> getOrdenTurnos() {
        List<String> orden = new ArrayList<>();
        // primero agregamos al que está jugando ahora
        if (entidadActual != null && entidadActual.estaVivo()) {
            orden.add(entidadActual.getNombre());
        }
        // después iteramos sobre los que esperan su turno en la ronda
        for (Entidad e : turnosDeLaRonda) {
            if (e.estaVivo()) {
                orden.add(e.getNombre());
            }
        }
        return orden;
    }
}