package modelo.gestores;

import modelo.entidades.personajes.Personaje;
import modelo.mundo.EscenarioDeNodos;
import modelo.mundo.Nodo;
import modelo.mundo.mapa.Mapa;
import modelo.party.Party;
import modelo.mundo.mapa.prefabs.Mapa1;
import modelo.mundo.mapa.prefabs.Mapa2;
import modelo.mundo.mapa.MapaPrefab;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GestorDeJuego {
    private static final int TAMANIO_PARTY = 3;

    private final List<MapaPrefab> mapasDisponibles;
    private final Random random;
    private Party party;
    private Mapa mapa;
    private EstadoJuego estado;


    // --- CONSTRUCTORES ---
    public GestorDeJuego() {
        this(Arrays.<MapaPrefab>asList(new Mapa1(), new Mapa2()), new Random());
    }

    public GestorDeJuego(List<MapaPrefab> mapasDisponibles, Random random) {
        this.mapasDisponibles = mapasDisponibles;
        this.random = random;
        this.estado = EstadoJuego.SIN_INICIAR;
    }


    // --- INICIO ---
    public void iniciarPartida(List<Personaje> personajes) {
        validarPersonajes(personajes);
        Party nuevaParty = new Party();
        for (Personaje p : personajes) nuevaParty.agregarPersonaje(p);

        this.party = nuevaParty;
        this.mapa = elegirMapaAlAzar().crear(nuevaParty);
        this.estado = EstadoJuego.EN_CURSO;
    }

    private MapaPrefab elegirMapaAlAzar() {
        return mapasDisponibles.get(random.nextInt(mapasDisponibles.size()));
    }

    // exactamente 3 personajes
    private void validarPersonajes(List<Personaje> personajes) {
        if (personajes == null || personajes.size() != TAMANIO_PARTY) {
            throw new IllegalArgumentException("Hay que elegir exactamente " + TAMANIO_PARTY + " personajes.");
        }
        for (Personaje p : personajes) {
            if (p == null) throw new IllegalArgumentException("Hay un personaje sin crear.");
        }
        if (new HashSet<>(personajes).size() != personajes.size()) {
            throw new IllegalArgumentException("No se puede usar dos veces el mismo personaje.");
        }
    }


    // --- RECORRIDO DEL MAPA ---
    public List<Nodo> getCaminosDisponibles() {
        verificarEnCurso();
        return mapa.getCaminosDisponibles();
    }

    public void avanzarA(Nodo destino, EscenarioDeNodos escenario) {
        verificarEnCurso();
        if (!mapa.getNodoActual().permiteAvanzar()) {
            throw new IllegalStateException("Todavía no se puede salir de este nodo.");
        }
        mapa.avanzarA(destino);
        destino.procesarNodo(escenario);
    }

    // se llama cuando el jugador termina con el nodo actual
    public void resolverNodoActual() {
        verificarEnCurso();
        if (party.estaDerrotada()) {
            estado = EstadoJuego.DERROTA;
        } else if (mapa.llegoAlFinal() && mapa.getNodoActual().permiteAvanzar()) {
            estado = EstadoJuego.VICTORIA;
        }
    }

    private void verificarEnCurso() {
        if (estado != EstadoJuego.EN_CURSO) {
            throw new IllegalStateException("La partida no está en curso.");
        }
    }


    // --- GETTERS ---
    public EstadoJuego getEstado() {return estado;}
    public Party getParty() {return party;}
    public Nodo getNodoActual() {return mapa.getNodoActual();}
}