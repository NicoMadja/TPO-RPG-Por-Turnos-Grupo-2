package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadTanque;

public class Tanque extends Personaje {

    // --- CONSTRUCTOR ---
    public Tanque(String nombre) {
        // tiene mucha defensa y vida, pero poco ataque y velocidad
        super(nombre, 1, 200, 30, 10, 30, 5, new HabilidadTanque());
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el tanque escala mucha defensa y vida moderada-alta, y poco ataque y velocidad
        this.vidaMaxima += 15;
        this.manaMaximo += 8;
        this.ataqueBase += 6;
        this.defensaBase += 12;
        this.velocidadBase += 2;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
