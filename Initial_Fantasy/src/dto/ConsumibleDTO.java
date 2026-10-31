package dto;

public class ConsumibleDTO {
    private final String nombre;
    private final int precio;
    private final String sprite;
    
    public ConsumibleDTO(String nombre, int precio, String sprite) {
        this.nombre = nombre;
        this.precio = precio;
        this.sprite = sprite;
    }

    public String getNombre() {return nombre;}
    public int getPrecio() {return precio;}
    public String getSprite() {return sprite;}
}

