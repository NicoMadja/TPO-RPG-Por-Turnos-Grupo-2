package modelo.mundo.mapa;


import modelo.mundo.mapa.prefabs.Mapa1;
import modelo.mundo.mapa.prefabs.Mapa2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FabricaMapas {

    private final Map<String, Supplier<MapaPrefab>> mapasRegistrados = new HashMap<>();

    public FabricaMapas() {
        registrarMapa("MAPA1", Mapa1::new);
        registrarMapa("MAPA2", Mapa2::new);
    }

    public void registrarMapa(String id, Supplier<MapaPrefab> creador) {
        mapasRegistrados.put(id, creador);
    }

    public MapaPrefab crearPrefab(String id) {
        Supplier<MapaPrefab> creador = mapasRegistrados.get(id);

        if (creador == null) {
            throw new IllegalArgumentException("Mapa desconocido: " + id);
        }

        return creador.get();
    }
}
