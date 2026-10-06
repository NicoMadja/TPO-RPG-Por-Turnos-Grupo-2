package modelo.habilidades;

import modelo.entidades.Entidad;

public abstract class Habilidad {
    protected String nombre;
    protected int costoMana;

    public Habilidad(String nombre, int costoMana) {
        this.nombre = nombre;
        this.costoMana = costoMana;
    }


    // ---  ---
    public abstract void ejecutar(Entidad lanzador, Entidad objetivo);


    // --- GETTERS ---
    public String getNombre() {return nombre;}

    public int getCostoMana() {return costoMana;}
}