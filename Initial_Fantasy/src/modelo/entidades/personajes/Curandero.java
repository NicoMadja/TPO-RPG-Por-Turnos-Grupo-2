package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadCurandero;

public class Curandero extends Personaje {

    // --- CONSTRUCTOR ---
    public Curandero(String nombre) {
        // tiene mucho maná y velocidad
        super(nombre, 1, 110, 120, 20, 10, 6, new HabilidadCurandero());
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el curandero escala mucho maná y moderado ataque, con poca velocidad
        this.vidaMaxima += 10;
        this.manaMaximo += 25;
        this.ataqueBase += 8;
        this.defensaBase += 6;
        this.velocidadBase += 2;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
