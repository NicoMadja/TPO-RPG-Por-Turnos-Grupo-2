package dto;

// datos de una entidad (héroe o enemigo) listos para que la vista los dibuje
public class EntidadDTO {
    private final String nombre;
    private final int vidaActual;
    private final int vidaMaxima;
    private final int manaActual;
    private final int manaMaximo;
    private final String sprite;
    private final boolean esTurnoActual;
    private final boolean defendiendo;

    public EntidadDTO(String nombre, int vidaActual, int vidaMaxima, int manaActual, int manaMaximo,
                      String sprite, boolean esTurnoActual, boolean defendiendo) {
        this.nombre = nombre;
        this.vidaActual = vidaActual;
        this.vidaMaxima = vidaMaxima;
        this.manaActual = manaActual;
        this.manaMaximo = manaMaximo;
        this.sprite = sprite;
        this.esTurnoActual = esTurnoActual;
        this.defendiendo = defendiendo;
    }

    public boolean estaVivo() {return vidaActual > 0;}

    public String getNombre() {return nombre;}
    public int getVidaActual() {return vidaActual;}
    public int getVidaMaxima() {return vidaMaxima;}
    public int getManaActual() {return manaActual;}
    public int getManaMaximo() {return manaMaximo;}
    public String getSprite() {return sprite;}
    public boolean esTurnoActual() {return esTurnoActual;}
    public boolean estaDefendiendo() {return defendiendo;}
}
