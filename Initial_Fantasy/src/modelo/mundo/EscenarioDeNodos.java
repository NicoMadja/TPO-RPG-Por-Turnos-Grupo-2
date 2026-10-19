package modelo.mundo;

import modelo.batalla.Batalla;
import modelo.campamento.Campamento;
import modelo.tienda.Tienda;

// lo implementa el controlador
public interface EscenarioDeNodos {
    void alEntrarEnBatalla(Batalla batalla);
    void alEntrarEnTienda(Tienda tienda);
    void alEntrarEnCampamento(Campamento campamento);
}