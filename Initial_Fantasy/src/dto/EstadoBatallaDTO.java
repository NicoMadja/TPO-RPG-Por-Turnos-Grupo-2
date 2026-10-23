
package dto;

import java.util.List;

// todo lo que la pantalla de batalla necesita para dibujarse
public class EstadoBatallaDTO {
    private final List<PersonajeDTO> heroes;
    private final List<EnemigoDTO> enemigos;
    private final List<ConsumibleDTO> items;
    private final FaseBatalla fase;
    private final EntidadDTO EntidadActual;

    public EstadoBatallaDTO(List<PersonajeDTO> heroes, List<EnemigoDTO> enemigos, List<ConsumibleDTO> items, FaseBatalla fase, EntidadDTO EntidadActual) {
        this.heroes = heroes;
        this.enemigos = enemigos;
        this.items = items;
        this.fase = fase;
        this.EntidadActual = EntidadActual;
    }

    // --- GETTERS ---

    public List<PersonajeDTO> getHeroes() {return heroes;}
    public List<EnemigoDTO> getEnemigos() {return enemigos;}
    public List<ConsumibleDTO> getItems() {return items;}
    public FaseBatalla getFase() {return fase;}
    public EntidadDTO getEntidadActual() {return EntidadActual;}
}

