package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadArquero;

public class Arquero extends Personaje {

    // --- CONSTRUCTOR ---
    public Arquero(String nombre) {
        // tiene mucho ataque y maná, pero poca vida
        super(nombre, 1, 90, 40, 22, 8, 10, new HabilidadArquero(), "/recursos/sprites/arquero.png");
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el arquero escala moderado maná y ataque
        this.vidaMaxima += 18;
        this.manaMaximo += 8;
        this.ataqueBase += 6;
        this.defensaBase += 2;
        this.velocidadBase += 3;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
