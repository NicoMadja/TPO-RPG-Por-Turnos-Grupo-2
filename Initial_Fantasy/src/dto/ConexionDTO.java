package dto;

public class ConexionDTO {
    private int origenX, origenY;
    private int destinoX, destinoY;

    // Constructor, getters

    public int getOrigenX() {
        return origenX;
    }

    public int getOrigenY() {
        return origenY;
    }

    public int getDestinoX() {
        return destinoX;
    }

    public int getDestinoY() {
        return destinoY;
    }

    public void setOrigenX(int origenX) {
        this.origenX = origenX;
    }

    public void setOrigenY(int origenY) {
        this.origenY = origenY;
    }

    public void setDestinoX(int destinoX) {
        this.destinoX = destinoX;
    }

    public void setDestinoY(int destinoY) {
        this.destinoY = destinoY;
    }
}