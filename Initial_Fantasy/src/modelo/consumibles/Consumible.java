package modelo.consumibles;

import modelo.entidades.Entidad;

public abstract class Consumible {
    protected String nombre;


    // --- CONSTRUCTOR ---
    public Consumible(String nombre) {
        this.nombre = nombre;
    }


    public abstract void consumir(Entidad objetivo);


    // --- GETTERS ---
    public String getNombre() {return nombre;}

}