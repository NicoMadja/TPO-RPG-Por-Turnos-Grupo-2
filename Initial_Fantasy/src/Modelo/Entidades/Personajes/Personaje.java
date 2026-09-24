package Modelo.Entidades.Personajes;

import Modelo.Entidades.Entidad;

public abstract class Personaje extends Entidad {
    protected int vidaMaxima;
    protected int manaMaximo;
    protected int experienciaBase;
    protected int experiencia;

    public void recibirCura(int cantidad){

    }

    public void recibirMana(int cantidad){

    }

    public void ganarExperiencia(int cantidad){

        if (experiencia > NO SE)
            subirNivel();
    }

    // Para mi es private porque el personaje sube de nivel, no lo suben desde afuera
    private void subirNivel(){

    }

    private void incrementarEstadisticas(){

    }

    @Override
    public int calcularAtaque(){
        return super.calcularAtaque();
    }

    @Override
    public int calcularDefensa() {
        return super.calcularDefensa();
    }

    @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(danio);
    }

    @Override
    public void recibirNerfeo(int cantidad) {
        super.recibirNerfeo(cantidad);
    }

    @Override
    public void recibirAumento(int cantidad) {
        super.recibirAumento(cantidad);
    }

    @Override
    public boolean estaMuerto() {
        return super.estaMuerto();
    }
}
