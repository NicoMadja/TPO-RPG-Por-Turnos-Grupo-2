package modelo.entidades.personajes;

import modelo.habilidades.HabilidadMago;

public class Mago extends Personaje {

    // --- CONSTRUCTOR ---
    public Mago(String nombre) {
        // tiene mucho maná y velocidad
        super(nombre, 1, 100, 120, 20, 10, 10, new HabilidadMago());
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el mago escala maná
        this.vidaMaxima += 12;
        this.manaMaximo += 10;
        this.ataqueBase += 6;
        this.defensaBase += 5;
        this.velocidadBase += 3;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
