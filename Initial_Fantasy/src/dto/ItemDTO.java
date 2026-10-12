package dto;

// un tipo de consumible del inventario con su cantidad
public class ItemDTO {
    private final String nombre;
    private final int cantidad;
    private final String sprite;

    public ItemDTO(String nombre, int cantidad, String sprite) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.sprite = sprite;
    }

    public String getNombre() {return nombre;}
    public int getCantidad() {return cantidad;}
    public String getSprite() {return sprite;}
}
