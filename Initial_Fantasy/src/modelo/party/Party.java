package modelo.party;

import modelo.entidades.personajes.Personaje;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private List<Personaje> miembros;
    private int monedero;
    private Inventario inventario;
    public boolean EquipoDerrotado; //??

    public Party() {
        this.miembros = new ArrayList<>();
    }

    public void AgregarPersonaje(Personaje p){
        if (miembros.size() < 3) {
            miembros.add(p);
            System.out.println("Personaje agregado.");
            return;
        }
        System.out.println("El equipo ya tiene el límite máximo de 3 integrantes.");
    }

    public void ObtenerRecompensas(int experiencia, int oro){
        agregrarOro(oro);
        // distribuir experiencia entre los personajes
    }

    public void agregrarOro(int cantidad){
        monedero += cantidad;
    }

    public void gastarOro(int cantidad){
        if (puedeGastarOro(cantidad))
            monedero -= cantidad;
        else
            System.out.println("No tiene suficiente oro.");
    }

    private boolean puedeGastarOro(int cantidad){
        return monedero > cantidad;
    }
}
