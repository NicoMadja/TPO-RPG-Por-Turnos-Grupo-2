package modelo.mundo.mapa.prefabs;

import modelo.consumibles.*;
import modelo.entidades.enemigos.*;
import modelo.mundo.mapa.Mapa;
import modelo.batalla.Batalla;
import modelo.campamento.Campamento;
import modelo.mundo.Nodo;
import modelo.mundo.mapa.MapaPrefab;
import modelo.tienda.Tienda;
import modelo.party.Party;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//   inicio ─┬─> batalla1 ─┬─> tienda ───────┐
//           │             └─> batalla3 ─────┼─> jefe final
//           └─> batalla2 ─┬─> batalla3      │
//                         └─> campamento ───┘
public class Mapa1 implements MapaPrefab {

    // Declaramos el mapa de coordenadas para guardar la posición visual
    private Map<Nodo, Point> coordenadas = new HashMap<>();

    @Override
    public Mapa crear(Party party) {
        Nodo inicio = new Campamento(party);
        Nodo batalla1 = new Batalla(party, enemigos(new Esqueleto("Esqueleto A", 2), new Esqueleto("Esqueleto B", 1), new Murcielago("Murcielago",1)));
        Nodo batalla2 = new Batalla(party, enemigos(new Murcielago("Murcielago A", 2), new Murcielago("Murcielago B", 1)));
        Nodo batalla3 = new Batalla(party, enemigos(new Brujo("Brujo", 2), new Murcielago("Muercilago",2), new Esqueleto("Esqueleto",3)));
        Nodo tienda = new Tienda(catalogo(), party);
        Nodo campamento = new Campamento(party);
        Nodo jefeFinal = new Batalla(party, enemigos(new MiniJefe("Demonio", 3),new Ogro("Ogro", 2), new Brujo("Brujo", 3))); // provisorio, hasta que exista JefeFinal

        Mapa mapa = new Mapa(inicio, jefeFinal,"/recursos/sprites/Mapa1.png","MAPA1");
        mapa.agregarNodo(batalla1);
        mapa.agregarNodo(batalla2);
        mapa.agregarNodo(batalla3);
        mapa.agregarNodo(tienda);
        mapa.agregarNodo(campamento);

        // --- CONEXIONES LÓGICAS ---
        mapa.conectar(inicio, batalla1);
        mapa.conectar(inicio, batalla2);

        mapa.conectar(batalla1, tienda);
        mapa.conectar(batalla1, batalla3);

        mapa.conectar(batalla2, batalla3);
        mapa.conectar(batalla2, campamento);

        mapa.conectar(tienda, jefeFinal);
        mapa.conectar(batalla3, jefeFinal);

        // Es vital descomentar esta conexión, sino el jugador se quedaría atascado en el campamento
        mapa.conectar(campamento, jefeFinal);


        // --- MAPEO DE COORDENADAS VISUALES PARA EL CONTROLADOR ---
        // Genera un rombo amplio y equilibrado en pantalla (basado en centro X=890)
        coordenadas.put(inicio, new Point(890, 850));       // Centro abajo

        coordenadas.put(batalla1, new Point(650, 650));     // Medio-Bajo Izquierda
        coordenadas.put(batalla2, new Point(1130, 650));    // Medio-Bajo Derecha

        coordenadas.put(tienda, new Point(450, 400));       // Alto Izquierda
        coordenadas.put(batalla3, new Point(890, 400));     // Alto Centro (Bifurcación que une batallas)
        coordenadas.put(campamento, new Point(1330, 400));  // Alto Derecha

        coordenadas.put(jefeFinal, new Point(890, 150));    // Centro arriba (Castillo)

        return mapa;
    }

    @Override
    public Map<Nodo, Point> getCoordenadasVisuales() {
        return coordenadas;
    }


    private List<Enemigo> enemigos(Enemigo... enemigos) {
        return new ArrayList<>(Arrays.asList(enemigos));
    }

    private List<Consumible> catalogo() {
        return new ArrayList<>(Arrays.<Consumible>asList(new PocionSalud(), new PocionMana(), new PocionFuerza(), new PocionDefensa()));
    }
}