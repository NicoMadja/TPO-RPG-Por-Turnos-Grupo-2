package modelo.mundo.nodos;

// lo implementa el controlador
public interface EscenarioDeNodos {
    void alEntrarEnBatalla(Batalla batalla);
    void alEntrarEnTienda(Tienda tienda);
    void alEntrarEnCampamento(Campamento campamento);
}