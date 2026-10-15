package modelo.habilidades;

import modelo.acciones.ContextoAccion;

public abstract class Habilidad {
    protected String nombre;
    protected int costoMana;
    protected TipoObjetivo tipoObjetivo;


    // --- CONSTRUCTOR ---
    public Habilidad(String nombre, int costoMana, TipoObjetivo tipoObjetivo) {
        this.nombre = nombre;
        this.costoMana = costoMana;
        this.tipoObjetivo = tipoObjetivo;
    }


    public abstract void ejecutar(ContextoAccion contexto);


    // --- GETTERS ---
    public String getNombre() {return nombre;}
    public int getCostoMana() {return costoMana;}
    public TipoObjetivo getTipoObjetivo() {return tipoObjetivo;}
}
