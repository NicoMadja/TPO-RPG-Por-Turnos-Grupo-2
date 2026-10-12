package dto;

import java.util.List;

// todo lo que la pantalla de batalla necesita para dibujarse
public class EstadoBatallaDTO {
    private final List<EntidadDTO> heroes;
    private final List<EntidadDTO> enemigos;
    private final List<ItemDTO> items;
    private final boolean turnoDeHeroe;

    public EstadoBatallaDTO(List<EntidadDTO> heroes, List<EntidadDTO> enemigos, List<ItemDTO> items, boolean turnoDeHeroe) {
        this.heroes = heroes;
        this.enemigos = enemigos;
        this.items = items;
        this.turnoDeHeroe = turnoDeHeroe;
    }

    public List<EntidadDTO> getHeroes() {return heroes;}
    public List<EntidadDTO> getEnemigos() {return enemigos;}
    public List<ItemDTO> getItems() {return items;}
    public boolean esTurnoDeHeroe() {return turnoDeHeroe;}
}
