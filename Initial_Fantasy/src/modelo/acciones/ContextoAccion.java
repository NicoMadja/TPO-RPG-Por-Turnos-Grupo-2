package modelo.acciones;

import modelo.entidades.Entidad;
import modelo.consumibles.Consumible;
import modelo.habilidades.Habilidad;
import java.util.ArrayList;
import java.util.List;

public class ContextoAccion {
    private Entidad origen;
    private List<Entidad> objetivos; // si es un solo objetivo, es una lista de un elemento
    private Consumible item;
    private Habilidad habilidad;


    // --- CONSTRUCTOR PARA UN SOLO OBJETIVO ---
    public ContextoAccion(Entidad origen, Entidad objetivo, Consumible item, Habilidad habilidad) {
        this.origen = origen;
        this.objetivos = new ArrayList<>();
        if (objetivo != null) this.objetivos.add(objetivo);
        this.item = item;
        this.habilidad = habilidad;
    }


    // --- CONSTRUCTOR PARA MAS DE UN OBJETIVO ---
    public ContextoAccion(Entidad origen, List<Entidad> objetivos, Consumible item, Habilidad habilidad) {
        this.origen = origen;
        this.objetivos = objetivos;
        this.item = item;
        this.habilidad = habilidad;
    }


    // --- GETTERS ---
    public Entidad getOrigen() { return origen; }
    public List<Entidad> getObjetivos() { return objetivos; }
    public Consumible getItem() { return item; }
    public Habilidad getHabilidad() { return habilidad; }
}