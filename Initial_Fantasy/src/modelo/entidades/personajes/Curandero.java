package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadCurandero;

public class Curandero extends Personaje {

    // --- CONSTRUCTOR ---
    public Curandero(String nombre) {
        // tiene mucho maná
        super(nombre, 1, 100, 70, 18, 6, 6, new HabilidadCurandero(), "/recursos/sprites/curandero.png");
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el curandero escala mucho maná y moderado ataque, con poca velocidad
        this.vidaMaxima += 16;
        this.manaMaximo += 12;
        this.ataqueBase += 4;
        this.defensaBase += 3;
        this.velocidadBase += 2;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
