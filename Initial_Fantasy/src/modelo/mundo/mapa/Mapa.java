package modelo.mundo.mapa;

import modelo.mundo.Grafo;
import modelo.mundo.Nodo;

import java.util.List;

// un nivel del juego: un grafo dirigido de nodos por el que se avanza solo hacia adelante, del inicio hasta el nodo final
public class Mapa {
    private final Grafo<Nodo> grafo;
    private final Nodo nodoInicial;
    private final Nodo nodoFinal;
    private Nodo nodoActual;


    // --- CONSTRUCTOR ---
    public Mapa(Nodo nodoInicial, Nodo nodoFinal) {
        this.grafo = new Grafo<>();
        this.nodoInicial = nodoInicial;
        this.nodoFinal = nodoFinal;
        this.nodoActual = nodoInicial;

        grafo.agregarVertice(nodoInicial);
        grafo.agregarVertice(nodoFinal);
    }


    // --- ARMADO ---
    public void agregarNodo(Nodo nodo) {
        grafo.agregarVertice(nodo);
    }

    // crea un camino: desde "origen" se puede avanzar hacia "destino"
    public void conectar(Nodo origen, Nodo destino) {
        grafo.agregarArista(origen, destino);
    }

    // el mapa está bien armado si desde el inicio se llega a todos los nodos, no hay caminos que vuelvan atrás
    // y el nodo final es el único en el que se termina (es decir, todos los caminos convergen en él)
    public boolean esValido() {
        if (!grafo.alcanzablesDesde(nodoInicial).containsAll(grafo.getVertices())) return false;
        if (grafo.tieneCiclos()) return false;

        for (Nodo nodo : grafo.getVertices()) {
            boolean sinSalida = grafo.getSucesores(nodo).isEmpty();
            if (sinSalida != (nodo == nodoFinal)) return false;
        }
        return true;
    }


    // --- RECORRIDO ---
    // los caminos que se le muestran al jugador desde donde está parado
    public List<Nodo> getCaminosDisponibles() {
        return grafo.getSucesores(nodoActual);
    }

    public void avanzarA(Nodo destino) {
        if (!grafo.existeArista(nodoActual, destino)) {
            throw new IllegalArgumentException("No se puede avanzar a ese nodo desde el nodo actual.");
        }
        nodoActual = destino;
    }

    public boolean llegoAlFinal() {
        return nodoActual == nodoFinal;
    }


    // --- GETTERS ---
    public Nodo getNodoActual() {return nodoActual;}
    public Nodo getNodoInicial() {return nodoInicial;}
    public Nodo getNodoFinal() {return nodoFinal;}
    public List<Nodo> getNodos() {return grafo.getVertices();}
}