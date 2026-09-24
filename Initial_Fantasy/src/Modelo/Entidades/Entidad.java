package Modelo.Entidades;

import Modelo.Habilidades.Habilidad;

public abstract class Entidad {
    protected String nombre;
    protected int vida;
    protected int nivel;
    protected Habilidad habilidad;
    protected int ataqueBase;
    protected int ataque;
    protected int defensaBase;
    protected int defensa;
    protected int velocidadBase;
    protected int velocidad;

    public int calcularAtaque(){

        return ataque??;
    }

    public int calcularDefensa(){

        return defensa??;
    }

    public void recibirDanio(int danio){
        vida -= danio;
    }

    // No se si es necesario hacer una funcion por cada tipo de estadistica que se aumenta/nerfea
    public void recibirAumento(int cantidad){

    }

    public void recibirNerfeo(int cantidad){

    }

    public boolean estaMuerto(){
        return vida <= 0;
    }
}
