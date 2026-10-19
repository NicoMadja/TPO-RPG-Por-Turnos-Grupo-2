package modelo.mundo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// grafo dirigido y sin pesos: cada vértice conoce a sus sucesores (a dónde se puede ir desde él)
public class Grafo<T> {
    private final Map<T, List<T>> sucesores = new LinkedHashMap<>(); // respeta el orden en que se agregaron


    // --- CONSTRUCCION ---
    public void agregarVertice(T vertice) {
        sucesores.putIfAbsent(vertice, new ArrayList<>());
    }

    public void agregarArista(T origen, T destino) {
        verificarExiste(origen);
        verificarExiste(destino);
        List<T> destinos = sucesores.get(origen);
        if (!destinos.contains(destino)) destinos.add(destino);
    }


    // --- CONSULTAS ---
    public boolean existeVertice(T vertice) {
        return sucesores.containsKey(vertice);
    }

    public boolean existeArista(T origen, T destino) {
        return existeVertice(origen) && sucesores.get(origen).contains(destino);
    }

    public List<T> getSucesores(T vertice) {
        verificarExiste(vertice);
        return Collections.unmodifiableList(sucesores.get(vertice));
    }

    public List<T> getVertices() {
        return Collections.unmodifiableList(new ArrayList<>(sucesores.keySet()));
    }

    // todos los vértices a los que se puede llegar desde "inicio" (incluido él mismo)
    public Set<T> alcanzablesDesde(T inicio) {
        verificarExiste(inicio);
        Set<T> visitados = new HashSet<>();
        Deque<T> pendientes = new ArrayDeque<>();
        visitados.add(inicio);
        pendientes.add(inicio);
        while (!pendientes.isEmpty()) {
            for (T destino : sucesores.get(pendientes.poll())) {
                if (visitados.add(destino)) pendientes.add(destino);
            }
        }
        return visitados;
    }

    // true si se puede volver a un vértice siguiendo las flechas
    public boolean tieneCiclos() {
        Map<T, Integer> estado = new HashMap<>(); // sin entrada = sin visitar, 1 = en curso, 2 = terminado
        for (T vertice : sucesores.keySet()) {
            if (hayCicloDesde(vertice, estado)) return true;
        }
        return false;
    }

    private boolean hayCicloDesde(T vertice, Map<T, Integer> estado) {
        Integer actual = estado.get(vertice);
        if (actual != null) return actual == 1;

        estado.put(vertice, 1);
        for (T destino : sucesores.get(vertice)) {
            if (hayCicloDesde(destino, estado)) return true;
        }
        estado.put(vertice, 2);
        return false;
    }

    private void verificarExiste(T vertice) {
        if (!sucesores.containsKey(vertice)) {
            throw new IllegalArgumentException("El vértice no pertenece al grafo.");
        }
    }
}