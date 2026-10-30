package modelo.entidades.personajes;

import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;

public abstract class Personaje extends Entidad {
    protected double experienciaActual;
    protected double experienciaRequerida; // para subir de nivel

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

    protected void incrementarEstadisticas() {
        // modificamos las estadísticas al subir de nivel
        this.vidaMaxima += 20; // POR AHORA SON VALORES RANDOM
        this.manaMaximo += 10;
        this.ataqueBase += 5;
        this.defensaBase += 5;
        this.velocidadBase += 2;

        // curamos al personaje al máximo
        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        // actualizamos los stats actuales para que reflejen los nuevos bases
        this.resetearEstadisticas();
    }

    public void restaurarExperiencia(
            double experienciaActual,
            double experienciaRequerida
    ) {
        this.experienciaActual = experienciaActual;
        this.experienciaRequerida = experienciaRequerida;
    }

    // --- GETTERS ---
    public double getExperienciaActual() { return experienciaActual; }
    public double getExperienciaRequerida() { return experienciaRequerida; }
}