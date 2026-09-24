package Modelo.Entidades.Enemigos;

import Modelo.Entidades.Entidad;
import Modelo.Entidades.Personajes.Personaje;

import java.util.List;

public abstract class Enemigo extends Entidad {
    protected int recompensaExperiencia;
    protected int recompensaOro;

    public Accion decidirAccion(List<Personaje> objetivo){
        return accionDecidida;
    }

    // Por ahí los dos soltar podrian ser uno solo, porque cumplen la misma funcion
    public void soltar(){

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
