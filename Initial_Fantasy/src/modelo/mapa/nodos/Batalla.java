package modelo.mapa.nodos;

import modelo.acciones.Accion;
import modelo.batalla.GestorDeTurnos;
import modelo.batalla.Recompensa;
import modelo.entidades.Entidad;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.personajes.Personaje;
import modelo.party.Party;
import java.util.ArrayList;
import java.util.List;

public class Batalla {
    private Party party;
    private List<Enemigo> enemigos;
    private GestorDeTurnos gestor;
    private Recompensa recompensa;
    private boolean finalizada;


    // --- CONSTRUCTOR ---
    public Batalla(Party party, List<Enemigo> enemigos) {
        this.party = party;
        this.enemigos = enemigos;
        this.gestor = new GestorDeTurnos();
        this.recompensa = calcularRecompensa();
        this.finalizada = false;
    }


    // --- FLUJO ---
    public void iniciarBatalla() {
        gestor.enlistarEntidades(party.getPersonajes(), enemigos);
    }

    // el controlador arma la acción del héroe y se la pasa acá
    public void ejecutarAccion(Accion accion) {
        accion.ejecutar();

        if (gestor.verificarFinBatalla()) {
            finalizarCombate();
        } else {
            gestor.avanzarTurno();
        }
    }

    // solo se llama si esTurnoDeEnemigo() es true
    public void ejecutarTurnoEnemigo() {
        Enemigo enemigo = (Enemigo) gestor.obtenerEntidadActual();
        ejecutarAccion(enemigo.decidirAccion(party));
    }

    private void finalizarCombate() {
        this.finalizada = true;

        // solo hay recompensa si ganaron los héroes
        if (gestor.enemigosDerrotados()) {
            party.sumarOro(recompensa.getOro());
            party.distribuirExperiencia(recompensa.getExperiencia());
        }

        resetearEstadisticasParty();
    }

    private void resetearEstadisticasParty() {
        for (Personaje p : party.getPersonajes()) { // tmb a los muertos
            p.resetearEstadisticas();
        }
    }

    private Recompensa calcularRecompensa() {
        int oro = 0;
        double experiencia = 0;
        for (Enemigo e : enemigos) {
            oro += e.getOroOtorgado();
            experiencia += e.getExperienciaOtorgada();
        }
        return new Recompensa(oro, experiencia);
    }


    // --- CONSULTAS ---
    public boolean esTurnoDeEnemigo() {return enemigos.contains(gestor.obtenerEntidadActual());}
    public boolean estaFinalizada() {return finalizada;}
    public boolean hayVictoria() {return finalizada && gestor.enemigosDerrotados();}


    // --- GETTERS ---
    public Party getParty() {return party;}
    public Entidad getEntidadActual() {return gestor.obtenerEntidadActual();}
    public Recompensa getRecompensa() {return recompensa;}
    public List<Enemigo> getEnemigos() {return enemigos;}
    public List<Enemigo> getEnemigosVivos() {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) {
            if (e.estaVivo()) vivos.add(e);
        }
        return vivos;
    }
}
