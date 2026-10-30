
package dto;

import java.util.List;

// Lo que la pantalla de batalla necesita para dibujarse
public class EstadoBatallaDTO {
    private final List<PersonajeDTO> heroes;
    private final List<EnemigoDTO> enemigos;
    private final List<ConsumibleDTO> items;
    private final FaseBatalla fase;
    private final EntidadDTO EntidadActual;
    private final String mensajeInfo;
    private final List<String> ordenTurnos;


    public EstadoBatallaDTO(List<PersonajeDTO> heroes, List<EnemigoDTO> enemigos, List<ConsumibleDTO> items, FaseBatalla fase, EntidadDTO EntidadActual, String mensajeInfo, List<String> ordenTurnos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
        this.items = items;
        this.fase = fase;
        this.EntidadActual = EntidadActual;
        this.mensajeInfo = mensajeInfo;
        this.ordenTurnos = ordenTurnos;
    }


    // --- GETTERS ---
    public List<PersonajeDTO> getHeroes() {return heroes;}
    public List<EnemigoDTO> getEnemigos() {return enemigos;}
    public List<ConsumibleDTO> getItems() {return items;}
    public FaseBatalla getFase() {return fase;}
    public EntidadDTO getEntidadActual() {return EntidadActual;}
    public String getMensajeInfo() {
        return mensajeInfo;}
    public List<String> getOrdenTurnos() {
        return ordenTurnos;
    }
}

