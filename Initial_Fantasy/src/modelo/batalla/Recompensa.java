package modelo.batalla;

public class Recompensa {
    private int oro;
    private double experiencia;


    // --- CONSTRUCTOR ---
    public Recompensa(int oro, double experiencia) {
        this.oro = oro;
        this.experiencia = experiencia;
    }


    // --- GETTERS ---
    public int getOro() {return oro;}
    public double getExperiencia() {return experiencia;}
}
