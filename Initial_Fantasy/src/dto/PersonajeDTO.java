package dto;

import java.io.Serializable;

public class PersonajeDTO extends EntidadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected double experienciaActual;
    protected double experienciaRequerida;

    public PersonajeDTO(String nombre, int nivel, int vidaMaxima, int vidaActual, int manaMaximo, int manaActual, int ataqueBase, int ataqueActual, int defensaBase, int defensaActual, boolean defendiendo, int velocidadBase, int velocidadActual, String sprite, double experienciaActual, double experienciaRequerida) {
        super(nombre, nivel, vidaMaxima, vidaActual, manaMaximo, manaActual, ataqueBase, ataqueActual, defensaBase, defensaActual, defendiendo, velocidadBase, velocidadActual, sprite);
        this.experienciaActual = experienciaActual;
        this.experienciaRequerida = experienciaRequerida;
    }
    
    public double getExperienciaActual() {
        return experienciaActual;
    }
    public double getExperienciaRequerida() {
        return experienciaRequerida;
    }
}
