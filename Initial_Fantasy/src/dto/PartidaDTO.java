package dto;

import modelo.party.Inventario;
import java.io.Serializable;
import java.util.List;

public class PartidaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<PersonajeDTO> heroesGuardados;
    private int idNodoActual;
    private Inventario inventarioGuardado;
    private String idMapa;

    public PartidaDTO(
            List<PersonajeDTO> heroesGuardados,
            int idNodoActual,
            Inventario inventario
    ) {
        this.heroesGuardados = heroesGuardados;
        this.idNodoActual = idNodoActual;
        this.inventarioGuardado = inventario;
    }

    public List<PersonajeDTO> getHeroesGuardados() {
        return heroesGuardados;
    }

    public int getIdNodoActual() {
        return idNodoActual;
    }

    public Inventario getInventarioGuardado() {
        return inventarioGuardado;
    }

    public String getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(String idMapa) {
        this.idMapa = idMapa;
    }
}
