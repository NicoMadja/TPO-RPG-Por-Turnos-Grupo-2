package Modelo.Entidades.Enemigos;

import Modelo.Entidades.Personajes.Personaje;

import java.util.List;

public class Murcielago extends Enemigo {

    @Override
    public Accion decidirAccion(List<Personaje> objetivo) {
        return super.decidirAccion(objetivo);
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
    public void recibirAumento(int cantidad) {
        super.recibirAumento(cantidad);
    }

    @Override
    public void recibirNerfeo(int cantidad) {
        super.recibirNerfeo(cantidad);
    }

    @Override
    public boolean estaMuerto() {
        return super.estaMuerto();
    }
}
