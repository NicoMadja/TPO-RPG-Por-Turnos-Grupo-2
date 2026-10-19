package modelo.mundo;

public abstract class Nodo {

    // --- CONSTRUCTOR ---
    public Nodo() {
    }

    // se llama cuando el jugador llega al nodo
    public abstract void procesarNodo(EscenarioDeNodos escenario);
}
