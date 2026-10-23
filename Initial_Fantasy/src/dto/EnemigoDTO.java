
package dto;

public class EnemigoDTO extends EntidadDTO{
    private double experienciaOtorgada;
    private int oroOtorgado;;

    public EnemigoDTO(String nombre, int nivel, int vidaMaxima, int vidaActual, int manaMaximo, int manaActual, int ataqueBase, int ataqueActual, int defensaBase, int defensaActual, boolean defendiendo, int velocidadBase, int velocidadActual, String sprite, int oroOtorgado, double experienciaOtorgada) {
        super(nombre, nivel, vidaMaxima, vidaActual, manaMaximo, manaActual, ataqueBase, ataqueActual, defensaBase, defensaActual, defendiendo, velocidadBase, velocidadActual, sprite);
        this.oroOtorgado = oroOtorgado;
        this.experienciaOtorgada = experienciaOtorgada;
    }

    public double getExperienciaOtorgada() {
        return experienciaOtorgada;
    }

    public int getOroOtorgado() {
        return oroOtorgado;
    }
}

