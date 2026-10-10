package modelo.party;

import modelo.entidades.personajes.Personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Party {
    private List<Personaje> personajes;
    private Inventario inventario;
    private int monedero;
    private Random random;


    // --- CONSTRUCTOR ---
    public Party() {
        this.personajes = new ArrayList<>();
        this.inventario = new Inventario();
        this.monedero = 0;
        this.random = new Random();
    }


    // --- AGREGAR ---
    public void agregarPersonaje(Personaje p) {
        if (personajes.size() < 3) {
            personajes.add(p);
            System.out.println("Personaje agregado.");
            return;
        }
        System.out.println("El equipo ya tiene el límite máximo de 3 integrantes.");
    }


    // --- FLUJO DE BATALLA ---
    public List<Personaje> getPersonajesVivos() {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) {
                vivos.add(p);
            }
        }
        return vivos;
    }

    public boolean estaDerrotada() {
        // la lista vacía sería que perdimos el juego
        return getPersonajesVivos().isEmpty();
    }


    // --- INTELIGENCIA PARA ENEMIGOS ---
    public Personaje obtenerPersonajeVivoAlAzar() {
        List<Personaje> vivos = getPersonajesVivos();
        if (vivos.isEmpty()) return null;

        return vivos.get(random.nextInt(vivos.size()));
    }

    public Personaje obtenerPersonajeConMenosVida() {
        List<Personaje> vivos = getPersonajesVivos();
        if (vivos.isEmpty()) return null;

        Personaje masDebil = vivos.get(0);
        for (Personaje p : vivos) {
            if (p.getVidaActual() < masDebil.getVidaActual()) {
                masDebil = p;
            }
        }
        return masDebil;
    }


    // --- ORO Y EXPERIENCIA ---
    public void distribuirExperiencia(int totalExp) {
        List<Personaje> vivos = getPersonajesVivos();
        if (vivos.isEmpty()) return;

        // se divide equitativamente entre los personajes que sobrevivieron
        int expPorPersonaje = totalExp / vivos.size();
        for (Personaje p : vivos) {
            p.ganarExperiencia(expPorPersonaje);
        }
    }

    public void sumarOro(int totalOro) {
        monedero += totalOro;
    }

    public boolean gastarOro(int cantidad){
        if (puedeGastarOro(cantidad)) {
            monedero -= cantidad;
            return true;
        } else {
            System.out.println("No tiene suficiente oro.");
            return false;
        }
    }

    public boolean puedeGastarOro(int cantidad){
        return monedero >= cantidad;
    }


    // --- GETTERS ---
    public List<Personaje> getPersonajes() {return personajes;}
    public Inventario getInventario() {return inventario;}
}