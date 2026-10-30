package modelo.habilidades;

import modelo.acciones.ContextoAccion;

public abstract class Habilidad {
    protected String nombre;
    protected String descripcion;
    protected int costoMana;
    protected TipoObjetivo tipoObjetivo;


    // --- CONSTRUCTOR ---
    public Habilidad(String nombre, String descripcion, int costoMana, TipoObjetivo tipoObjetivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoMana = costoMana;
        this.tipoObjetivo = tipoObjetivo;
    }


    public abstract void ejecutar(ContextoAccion contexto);


    // --- GETTERS ---
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
    public int getCostoMana() {return costoMana;}
    public TipoObjetivo getTipoObjetivo() {return tipoObjetivo;}
}
