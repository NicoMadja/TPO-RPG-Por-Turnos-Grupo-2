package modelo.consumibles;

import modelo.entidades.Entidad;

public abstract class Consumible {
    protected String nombre;
    protected int precio;


    // --- CONSTRUCTOR ---
    public Consumible(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }


    public abstract void consumir(Entidad objetivo);


    // --- GETTERS ---
    public String getNombre() {return nombre;}
    public int getPrecio() {return precio;}
}
