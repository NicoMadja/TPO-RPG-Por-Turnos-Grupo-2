package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadGuerrero;

public class Guerrero extends Personaje {

    // --- CONSTRUCTOR ---
    public Guerrero(String nombre) {
        // tiene bastante vida y ataque, pero poco maná; es balanceado
        super(nombre, 1, 140, 40, 22, 13, 9, new HabilidadGuerrero(), "/recursos/sprites/guerrero.png");
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el Guerrero escala mucho mejor en vida y ataque
        this.vidaMaxima += 25;
        this.manaMaximo += 5;
        this.ataqueBase += 5;
        this.defensaBase += 4;
        this.velocidadBase += 3;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}