package dto;

public class NodoDTO {
    private int id; // Un identificador único para el botón
    private String tipo; // "BATALLA", "TIENDA", "CAMPAMENTO", "JEFE"
    private int x; // Posición X en la pantalla
    private int y; // Posición Y en la pantalla
    private boolean habilitado; // Si el jugador puede hacer clic aquí ahora mismo

    // Constructor, getters y setters...

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}