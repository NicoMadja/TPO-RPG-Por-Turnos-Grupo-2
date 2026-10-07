package modelo.consumibles;

import modelo.entidades.Entidad;

public abstract class Consumible {
    protected String nombre;


    // --- CONSTRUCTOR ---
    public Consumible(String nombre) {
        this.nombre = nombre;
    }


    // --- GETTERS ---
    public String getNombre() {return nombre;}


    public abstract void consumir(Entidad objetivo);
}