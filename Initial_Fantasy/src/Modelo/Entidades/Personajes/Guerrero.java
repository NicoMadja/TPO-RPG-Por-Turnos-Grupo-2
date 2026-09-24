package Modelo.Entidades.Personajes;

public class Guerrero extends Personaje {

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
