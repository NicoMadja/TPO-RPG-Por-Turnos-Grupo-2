package dto;

public class EnemigoDTO {
    private String nombre;
    private int nivel;

    private int vidaMaxima;
    private int vidaActual;
    private int manaMaximo;
    private int manaActual;

    private int ataqueBase;
    private int ataqueActual;

    private int defensaBase;
    private int defensaActual;
    private boolean defendiendo;

    private int velocidadBase;
    private int velocidadActual;

    private double experienciaOtorgada;
    private int oroOtorgado;;

    private String sprite;


    public EnemigoDTO(String nombre, int nivel, int vidaMaxima, int vidaActual, int manaMaximo, int manaActual, int ataqueBase,
                      int ataqueActual, int defensaBase, int defensaActual, boolean defendiendo, int velocidadBase,
                      int velocidadActual, double experienciaOtorgada, int oroOtorgado, String sprite) {
        this.nombre = nombre;
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
        this.experienciaOtorgada = experienciaOtorgada;
        this.oroOtorgado = oroOtorgado;
        this.sprite = sprite;
    }


    // -- GETTERS Y SETTERS ---
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
    public boolean isDefendiendo() {return defendiendo;}
    public int getVelocidadBase() {return velocidadBase;}
    public int getVelocidadActual() {return velocidadActual;}
    public double getExperienciaOtorgada() {return experienciaOtorgada;}
    public int getOroOtorgado() {return oroOtorgado;}
    public String getSprite() {return sprite;}
}
