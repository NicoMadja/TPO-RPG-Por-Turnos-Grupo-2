package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadMago;

public class Mago extends Personaje {

    // --- CONSTRUCTOR ---
    public Mago(String nombre) {
        // tiene mucho maná y velocidad
        super(nombre, 1, 100, 80, 25, 5, 8, new HabilidadMago(), "/recursos/sprites/mago.png");
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el mago escala maná
        this.vidaMaxima += 12;
        this.manaMaximo += 15;
        this.ataqueBase += 6;
        this.defensaBase += 2;
        this.velocidadBase += 3;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
