package modelo.habilidades;

import modelo.acciones.ContextoAccion;

public abstract class Habilidad {
    protected String nombre;
    protected int costoMana;


    // --- CONSTRUCTOR ---
    public Habilidad(String nombre, int costoMana) {
        this.nombre = nombre;
        this.costoMana = costoMana;
    }


    public abstract void ejecutar(ContextoAccion contexto);


    // --- GETTERS ---
    public String getNombre() {return nombre;}
    public int getCostoMana() {return costoMana;}
}
