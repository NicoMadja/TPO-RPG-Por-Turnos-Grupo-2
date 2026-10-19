package modelo.mundo.mapa;

import modelo.party.Party;

// receta para armar un mapa ya listo (con sus nodos y caminos)
public interface MapaPrefab {
    Mapa crear(Party party);
}