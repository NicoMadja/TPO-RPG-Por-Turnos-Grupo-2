package modelo.entidades;

import modelo.habilidades.Habilidad;

import java.io.Serializable;

public abstract class Entidad implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nombre;
    protected int nivel;

    // Vida y Maná
    protected int vidaMaxima;
    protected int vidaActual;
    protected int manaMaximo;
    protected int manaActual;

    // Estadísticas
    protected int ataqueBase;
    protected int ataqueActual;

    protected int defensaBase;
    protected int defensaActual;
    // Defendiendo como acción (no pasiva)
    protected boolean defendiendo;

    protected int velocidadBase;
    protected int velocidadActual;

    // Habilidad
    protected Habilidad habilidad;

    // Sprite
    protected String rutaSprite;


    // --- CONSTRUCTOR ---
    public Entidad(String nombre, int nivel, int vidaMaxima, int manaMaximo, int ataqueBase, int defensaBase, int velocidadBase, Habilidad habilidad, String rutaSprite) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.manaMaximo = manaMaximo;
        this.ataqueBase = ataqueBase;
        this.defensaBase = defensaBase;
        this.velocidadBase = velocidadBase;
        this.habilidad = habilidad;
        this.defendiendo = false;
        this.rutaSprite = rutaSprite;

        // una entidad empieza con los valores actuales como máximos
        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;
        this.ataqueActual = this.ataqueBase;
        this.defensaActual = this.defensaBase;
        this.velocidadActual = this.velocidadBase;
    }



    // --- COMBATE ---
    public void recibirDanio(int danio) {
        this.vidaActual -= danio;
        // para que la vida no baje de 0
        if (this.vidaActual < 0) this.vidaActual = 0;
    }

    public void recibirCura(int cantidad) {
        this.vidaActual += cantidad;
        // para que no se cure por encima del máximo
        if (this.vidaActual > this.vidaMaxima) this.vidaActual = this.vidaMaxima;
    }

    public void gastarMana(int cantidad) {
        this.manaActual -= cantidad;
        // para que el maná no baje de 0
        if (this.manaActual < 0) this.manaActual = 0;
    }

    public void recuperarMana(int cantidad) {
        this.manaActual += cantidad;
        // para que no recupere por encima del máximo
        if (this.manaActual > this.manaMaximo) this.manaActual = this.manaMaximo;
    }


    // --- BUFFS Y NERFS --- numero negativo = nerf; numero positivo = buff
    public void modificarAtaque(int cantidad) {
        this.ataqueActual += cantidad;
        // evitamos que el ataque quede en números negativos
        if (this.ataqueActual < 0) this.ataqueActual = 0;
    }

    public void modificarDefensa(int cantidad) {
        this.defensaActual += cantidad;
        // evitamos que la defensa quede en números negativos
        if (this.defensaActual < 0) this.defensaActual = 0;
    }

    public void modificarVelocidad(int cantidad) {
        this.velocidadActual += cantidad;
        if (this.velocidadActual < 0) this.velocidadActual = 0;
    }

    public void resetearEstadisticas() {
        // se llama al terminar la batalla para limpiar
        this.ataqueActual = this.ataqueBase;
        this.defensaActual = this.defensaBase;
        this.velocidadActual = this.velocidadBase;
        this.defendiendo = false;
    }


    // --- ESTADOS ---
    public boolean estaVivo() {
        return this.vidaActual > 0;
    }

    public boolean tieneSuficienteMana(int cantidad) {
        return this.manaActual >= cantidad;
    }

    // limpia lo que dura hasta su siguiente turno
    public void iniciarTurno() {
        this.defendiendo = false;
    }

    public void restaurarEstado(
            int nivel,
            int vidaMaxima,
            int vidaActual,
            int manaMaximo,
            int manaActual,
            int ataqueBase,
            int ataqueActual,
            int defensaBase,
            int defensaActual,
            boolean defendiendo,
            int velocidadBase,
            int velocidadActual
    ) {
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaActual;
        this.manaMaximo = manaMaximo;
        this.manaActual = manaActual;
        this.ataqueBase = ataqueBase;
        this.ataqueActual = ataqueActual;
        this.defensaBase = defensaBase;
        this.defensaActual = defensaActual;
        this.defendiendo = defendiendo;
        this.velocidadBase = velocidadBase;
        this.velocidadActual = velocidadActual;
    }

    // --- GETTERS ---
    public String getNombre() {return nombre;}
    public int getNivel() {return nivel;}
    public int getVidaMaxima() {return vidaMaxima;}
    public int getVidaActual() {return vidaActual;}
    public int getManaMaximo() {return manaMaximo;}
    public int getManaActual() {return manaActual;}
    public int getAtaqueBase() {return ataqueBase;}
    public int getAtaqueActual() {return ataqueActual;}
    public int getDefensaBase() {return defensaBase;}
    public int getDefensaActual() {return defensaActual;}
    public int getVelocidadBase() {return velocidadBase;}
    public int getVelocidadActual() {return velocidadActual;}
    public Habilidad getHabilidad() {return habilidad;}
    public String getRutaSprite() {return rutaSprite;}
    public boolean isDefendiendo() {return defendiendo;}


    // --- SETTERS ---
    public void setDefendiendo(boolean defendiendo) {this.defendiendo = defendiendo;}
}
