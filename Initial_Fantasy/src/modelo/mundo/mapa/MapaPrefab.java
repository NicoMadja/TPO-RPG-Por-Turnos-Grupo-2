package modelo.mundo.mapa;

import modelo.mundo.Nodo;
import modelo.party.Party;
import java.awt.*;
import java.util.Map;

public interface MapaPrefab {
    Mapa crear(Party party);

    Map<Nodo, Point> getCoordenadasVisuales();
}