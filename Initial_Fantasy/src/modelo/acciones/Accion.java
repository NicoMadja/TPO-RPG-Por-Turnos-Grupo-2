package modelo.acciones;

public abstract class Accion {
    protected String nombre;

    public abstract void ejecutar(ContextoAccion contexto);
}
