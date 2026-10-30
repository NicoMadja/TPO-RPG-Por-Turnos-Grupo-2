package modelo.party;

import modelo.entidades.personajes.Personaje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Party implements Serializable {
    private static final long serialVersionUID = 1L;
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
    public List<Personaje> distribuirExperiencia(double totalExp) {
        List<Personaje> subieronDeNivel = new ArrayList<>();
        List<Personaje> vivos = getPersonajesVivos();
        if (vivos.isEmpty()) return subieronDeNivel;

        double expPorPersonaje = totalExp / vivos.size();
        for (Personaje p : vivos) {
            int nivelAntes = p.getNivel();
            p.ganarExperiencia(expPorPersonaje);
            if (p.getNivel() > nivelAntes) subieronDeNivel.add(p);
        }
        return subieronDeNivel;
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

    private boolean puedeGastarOro(int cantidad){
        return monedero >= cantidad;
    }

    public void setInventario(Inventario inventarioCargado) {
        if (inventarioCargado != null) {
            this.inventario = inventarioCargado;
        }
    }
    // --- GETTERS ---
    public List<Personaje> getPersonajes() {return personajes;}
    public Inventario getInventario() {return inventario;}
    public int getMonedero() {return monedero;}
}