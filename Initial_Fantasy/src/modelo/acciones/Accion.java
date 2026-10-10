package modelo.acciones;

public abstract class Accion {
    protected ContextoAccion contexto;


    // --- CONSTRUCTOR ---
    public Accion(ContextoAccion contexto) {
        this.contexto = contexto;
    }


    public abstract void ejecutar();
}
