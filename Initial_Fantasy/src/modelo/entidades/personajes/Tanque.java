package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadTanque;

public class Tanque extends Personaje {

    // --- CONSTRUCTOR ---
    public Tanque(String nombre) {
        // tiene mucha defensa y vida, pero poco ataque y velocidad
        super(nombre, 1, 180, 20, 14, 16, 10, new HabilidadTanque(), "/recursos/sprites/tanque.png");
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        
        // el tanque escala mucha defensa y vida moderada-alta, y poco ataque y velocidad
        this.vidaMaxima += 25;
        this.manaMaximo += 5;
        this.ataqueBase += 3;
        this.defensaBase += 4;
        this.velocidadBase += 3;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
