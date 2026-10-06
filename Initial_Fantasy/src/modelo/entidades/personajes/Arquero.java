package modelo.entidades.personajes;

import modelo.habilidades.aliadas.HabilidadArquero;

public class Arquero extends Personaje {

    // --- CONSTRUCTOR ---
    public Arquero(String nombre) {
        // tiene mucho ataque y maná, pero poca vida
        super(nombre, 1, 80, 100, 30, 10, 10, new HabilidadArquero());
    }

    // --- PROGRESIÓN ESPECÍFICA ---
    @Override
    protected void incrementarEstadisticas() {
        // el arquero escala moderado maná y ataque, con un poco de velocidad
        this.vidaMaxima += 10;
        this.manaMaximo += 10;
        this.ataqueBase += 10;
        this.defensaBase += 5;
        this.velocidadBase += 4;

        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;

        this.resetearEstadisticas();
    }
}
