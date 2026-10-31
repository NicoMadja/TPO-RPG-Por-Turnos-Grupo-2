package modelo.mundo.mapa;

import modelo.mundo.Grafo;
import modelo.mundo.Nodo;

import java.io.Serializable;
import java.util.List;

public class Mapa implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Grafo<Nodo> grafo;
    private final Nodo nodoInicial;
    private final Nodo nodoFinal;
    private Nodo nodoActual;
    private String sprite;
    private String idMapa;


    // --- CONSTRUCTOR ---
    public Mapa(Nodo nodoInicial, Nodo nodoFinal,String sprite,String idMapa) {
        this.grafo = new Grafo<>();
        this.nodoInicial = nodoInicial;
        this.nodoFinal = nodoFinal;
        this.nodoActual = nodoInicial;
        this.sprite = sprite;
        this.idMapa = idMapa;
        grafo.agregarVertice(nodoInicial);
        grafo.agregarVertice(nodoFinal);
    }


    // --- ARMADO ---
    public void agregarNodo(Nodo nodo) {
        grafo.agregarVertice(nodo);
    }

    // crea un camino
    public void conectar(Nodo origen, Nodo destino) {
        grafo.agregarArista(origen, destino);
    }

    // el mapa está bien armado si desde el inicio se llega a todos los nodos, no hay caminos que vuelvan atrás
    // y el nodo final es el único en el que se termina (es decir, todos los caminos convergen en él); lo usamos como
    // verificador de que el mapa está correctamente armado
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

    public void restaurarNodoActual(int idNodo) {

        List<Nodo> nodos = getNodos();

        if (idNodo < 0 || idNodo >= nodos.size()) {
            throw new IllegalArgumentException(
                    "El identificador del nodo guardado no es válido: "
                            + idNodo
            );
        }

        this.nodoActual = nodos.get(idNodo);
    }

    // --- GETTERS ---
    public Nodo getNodoActual() {return nodoActual;}
    public Nodo getNodoInicial() {return nodoInicial;}
    public Nodo getNodoFinal() {return nodoFinal;}
    public List<Nodo> getNodos() {return grafo.getVertices();}
    public String getSprite() {
        return sprite;
    }
    public String getIdMapa() {return idMapa;}
    public List<Nodo> getSucesoresDe(Nodo nodo) {
        return grafo.getSucesores(nodo);
    }

    // --- SETTERS ---
    public void setIdMapa(String idMapa) {this.idMapa = idMapa;}
}