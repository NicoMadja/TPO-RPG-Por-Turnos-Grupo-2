package modelo.entidades.enemigos;

import modelo.acciones.Accion;
import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.party.Party;

public abstract class Enemigo extends Entidad {
    protected int experienciaOtorgada;
    protected int oroOtorgado;

    // --- CONSTRUCTOR ---
    public Enemigo(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataqueBase, int defensaBase, int velocidadBase, Habilidad habilidad, int experienciaOtorgada, int oroOtorgado) {
        super(nombre, nivel, vidaMaxima, manaMaximo, ataqueBase, defensaBase, velocidadBase, habilidad);
        // recompensas
        this.experienciaOtorgada = experienciaOtorgada;
        this.oroOtorgado = oroOtorgado;
    }

    // --- DECIDIR ACCION ---
    // el GestorDeTurnos llama a este método cuando le toque jugar al enemigo
    public abstract Accion decidirAccion(Party heroes);


    // --- GETTERS ---
    public int getExperienciaOtorgada() {return experienciaOtorgada;}
    public int getOroOtorgado() {return oroOtorgado;}
}
