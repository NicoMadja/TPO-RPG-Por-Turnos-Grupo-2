package modelo.mapa.nodos;

import java.util.List;
import modelo.entidades.enemigos.Enemigo;
import modelo.party.Party;

public class Batalla {
    public List<Enemigo> enemigos;
    public Party party;
    public GestorDeTurnos gestor;
    public boolean completada;
    public Recompensa recompensa;



}
