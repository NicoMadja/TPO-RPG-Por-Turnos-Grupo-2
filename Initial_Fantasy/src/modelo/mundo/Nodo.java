package modelo.mundo;

public abstract class Nodo {

    // --- CONSTRUCTOR ---
    public Nodo() {
    }

    // si el jugador puede irse de este nodo hacia el siguiente
    public boolean permiteAvanzar() {
        return true;
    }

    // se llama cuando el jugador llega al nodo
    public abstract void procesarNodo(EscenarioDeNodos escenario);
}
