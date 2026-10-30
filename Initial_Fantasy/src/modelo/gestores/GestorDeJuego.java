package modelo.gestores;

import modelo.mundo.mapa.MapaPrefab;
import modelo.mundo.mapa.prefabs.Mapa1;
import modelo.mundo.mapa.prefabs.Mapa2;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GestorDeJuego {

    private final List<MapaPrefab> mapasDisponibles;
    private final Random random;

    public GestorDeJuego() {
        this(
                Arrays.<MapaPrefab>asList(
                        new Mapa1(),
                        new Mapa2()
                ),
                new Random()
        );
    }

    public GestorDeJuego(
            List<MapaPrefab> mapasDisponibles,
            Random random) {

        if (mapasDisponibles == null || mapasDisponibles.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe existir al menos un mapa disponible."
            );
        }

        if (random == null) {
            throw new IllegalArgumentException(
                    "El generador aleatorio no puede ser null."
            );
        }

        this.mapasDisponibles = List.copyOf(mapasDisponibles);
        this.random = random;
    }

    public MapaPrefab elegirMapaAlAzar() {
        int indice = random.nextInt(mapasDisponibles.size());

        return mapasDisponibles.get(indice);
    }
}