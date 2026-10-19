package modelo.mundo.mapa.prefabs;

import modelo.consumibles.*;
import modelo.entidades.enemigos.Brujo;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.enemigos.Esqueleto;
import modelo.entidades.enemigos.Murcielago;
import modelo.entidades.enemigos.Ogro;
import modelo.mundo.mapa.Mapa;
import modelo.batalla.Batalla;
import modelo.campamento.Campamento;
import modelo.mundo.Nodo;
import modelo.mundo.mapa.MapaPrefab;
import modelo.tienda.Tienda;
import modelo.party.Party;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//   inicio ───> batalla1 ─┬─> batalla2 ───> tienda ──────┐
//                         │                              ┼─> batalla4 ───> jefe final
//                         └─> batalla3 ───> campamento ──┘
public class Mapa2 implements MapaPrefab {

    @Override
    public Mapa crear(Party party) {
        Nodo inicio = new Campamento(party);
        Nodo batalla1 = new Batalla(party, enemigos(new Esqueleto("Esqueleto A", 1), new Murcielago("Murcielago A", 1)));
        Nodo batalla2 = new Batalla(party, enemigos(new Murcielago("Murcielago A", 2), new Murcielago("Murcielago B", 2), new Esqueleto("Esqueleto A", 2)));
        Nodo batalla3 = new Batalla(party, enemigos(new Murcielago("Murcielago A", 2), new Esqueleto("Esqueleto A", 2), new Brujo("Brujo A", 2)));
        Nodo batalla4 = new Batalla(party, enemigos(new Murcielago("Murcielago A", 3), new Esqueleto("Esqueleto A", 3)));
        Nodo tienda = new Tienda(catalogo(), party);
        Nodo campamento = new Campamento(party);
        Nodo jefeFinal = new Batalla(party, enemigos(new Ogro("Ogro Jefe", 5))); // provisorio, hasta que exista JefeFinal

        Mapa mapa = new Mapa(inicio, jefeFinal);
        mapa.agregarNodo(batalla1);
        mapa.agregarNodo(batalla2);
        mapa.agregarNodo(batalla3);
        mapa.agregarNodo(batalla4);
        mapa.agregarNodo(tienda);
        mapa.agregarNodo(campamento);

        mapa.conectar(inicio, batalla1);
        mapa.conectar(batalla1, batalla2);
        mapa.conectar(batalla1, batalla3);
        mapa.conectar(batalla2, tienda);
        mapa.conectar(batalla3, campamento);
        mapa.conectar(tienda, batalla4);
        mapa.conectar(campamento, batalla4);
        mapa.conectar(batalla4, jefeFinal);
        return mapa;
    }

    private List<Enemigo> enemigos(Enemigo... enemigos) {
        return new ArrayList<>(Arrays.asList(enemigos));
    }

    private List<Consumible> catalogo() {
        return new ArrayList<>(Arrays.<Consumible>asList(new PocionSalud(), new PocionMana(), new PocionFuerza(), new PocionDefensa()));
    }
}