package modelo.consumibles;

import modelo.entidades.Entidad;

public abstract class Consumible {
    protected String nombre;
    protected int precio;
    protected String rutaSprite;


    // --- CONSTRUCTOR ---
    public Consumible(String nombre, int precio, String rutaSprite) {
        this.nombre = nombre;
        this.precio = precio;
        this.rutaSprite = rutaSprite;
    }


    public abstract void consumir(Entidad objetivo);


    // --- GETTERS ---
    public String getNombre() {return nombre;}
    public int getPrecio() {return precio;}
    public String getRutaSprite() {return rutaSprite;}
}
