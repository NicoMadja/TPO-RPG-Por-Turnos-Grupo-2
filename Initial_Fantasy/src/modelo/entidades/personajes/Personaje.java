package modelo.entidades.personajes;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public abstract class Personaje extends Entidad {
    protected double experienciaActual;
    protected double experienciaRequerida;


    // --- CONSTRUCTOR ---
    public Personaje(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataqueBase, int defensaBase, int velocidadBase, Habilidad habilidad, String rutaSprite) {
        super(nombre, nivel, vidaMaxima, manaMaximo, ataqueBase, defensaBase, velocidadBase, habilidad, rutaSprite);
        this.experienciaActual = 0;
        // para pasar a nivel 2 necesita 100, a nivel 3 necesita 200, y así sucesivamente
        this.experienciaRequerida = nivel * 100;
    }


    // --- EXPERIENCIA Y NIVEL ---
    public void ganarExperiencia(double cantidad) {
        this.experienciaActual += cantidad;
        // usamos while por si sube mas de un nivel de una sola adquisición de experiencia
        while (this.experienciaActual >= this.experienciaRequerida) subirNivel();
    }

    protected void subirNivel() {
        this.nivel++;

        // restamos la exp usada para subir de nivel, y así no se pierde el excedente
        this.experienciaActual -= this.experienciaRequerida;

        // próximo nivel
        this.experienciaRequerida = this.nivel * 100;

        incrementarEstadisticas();
    }

    public void restaurarExperiencia(double experienciaActual, double experienciaRequerida) {
        this.experienciaActual = experienciaActual;
        this.experienciaRequerida = experienciaRequerida;
    }

    // cada héroe lo hace a su manera, porque cada uno escala distinto
    protected abstract void incrementarEstadisticas();


    // --- GETTERS ---
    public double getExperienciaActual() { return experienciaActual; }
    public double getExperienciaRequerida() { return experienciaRequerida; }
}