package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadGuerrero;

public class Guerrero extends Personaje {

    // --- CONSTRUCTOR ---
    public Guerrero(String nombre) {
        // tiene mucha vida y ataque, pero poco maná
        super(nombre, 1, 150, 20, 25, 15, 10, new HabilidadGuerrero());
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el Guerrero escala mucho mejor en vida y ataque
        this.vidaMaxima += 15;
        this.manaMaximo += 5;
        this.ataqueBase += 10;
        this.defensaBase += 8;
        this.velocidadBase += 3;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}