package modelo.batalla;

import modelo.acciones.Accion;
import modelo.acciones.AccionAtaque;
import modelo.acciones.AccionConsumible;
import modelo.acciones.AccionDefensa;
import modelo.acciones.AccionHabilidad;
import modelo.acciones.ContextoAccion;
import modelo.consumibles.Consumible;
import modelo.gestores.GestorDeTurnos;
import modelo.entidades.Entidad;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.personajes.Personaje;
import modelo.mundo.EscenarioDeNodos;
import modelo.mundo.Nodo;
import modelo.party.Party;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.habilidades.TipoObjetivo;

public class Batalla extends Nodo {
    private Party party;
    private List<Enemigo> enemigos;
    private GestorDeTurnos gestor;
    private Recompensa recompensa;
    private boolean finalizada;
    private List<Personaje> subieronDeNivel = new ArrayList<>();
    private boolean jefeFinal;

    // --- CONSTRUCTOR ---
    public Batalla(Party party, List<Enemigo> enemigos,boolean jefeFinal) {
        this.party = party;
        this.enemigos = enemigos;
        this.gestor = new GestorDeTurnos();
        this.recompensa = calcularRecompensa();
        this.finalizada = false;
        this.jefeFinal = jefeFinal;
    }


    // --- FLUJO ---
    public void iniciarBatalla() {
        utils.BitacoraBatalla.limpiar();
        utils.BitacoraBatalla.registrar("Iniciando batalla...");
        gestor.enlistarEntidades(party.getPersonajes(), enemigos);
    }

    private void ejecutarAccion(Accion accion) {
        accion.ejecutar();

        if (gestor.verificarFinBatalla()) {
            finalizarCombate();
        } else {
            gestor.avanzarTurno();
        }
    }

    // el controlador no debería llamar a una acción de héroe fuera de su turno: si pasa, es un bug y tiene que notarse
    private void verificarTurnoDeHeroe() {
        if (finalizada) throw new IllegalStateException("La batalla ya terminó.");
        if (esTurnoDeEnemigo()) throw new IllegalStateException("No es el turno de un héroe.");
    }

    private void finalizarCombate() {
        this.finalizada = true;

        // hay recompensa si ganaron los héroes
        if (gestor.enemigosDerrotados()) {
            party.sumarOro(recompensa.getOro());
            subieronDeNivel = party.distribuirExperiencia(recompensa.getExperiencia());
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

    public boolean permiteAvanzar() {
        return hayVictoria();
    }


    // --- ACCIONES JUGADOR ---
    public void atacar(Entidad objetivo) {
        verificarTurnoDeHeroe();
        ContextoAccion contexto = new ContextoAccion(getEntidadActual(), objetivo, null, null);
        ejecutarAccion(new AccionAtaque(contexto));
    }

    public void defender() {
        verificarTurnoDeHeroe();
        ContextoAccion contexto = new ContextoAccion(getEntidadActual(), (Entidad) null, null, null);
        ejecutarAccion(new AccionDefensa(contexto));
    }

    // habilidad de un solo objetivo (UN_ENEMIGO o UN_ALIADO)
    public void usarHabilidad(Entidad objetivo) {
        ejecutarHabilidad(Collections.singletonList(objetivo));
    }

    // habilidad de grupo (TODOS_LOS_ENEMIGOS o TODOS_LOS_ALIADOS)
    public void usarHabilidad() {
        verificarTurnoDeHeroe();
        TipoObjetivo tipo = getEntidadActual().getHabilidad().getTipoObjetivo();

        List<Entidad> objetivos = new ArrayList<>();
        if (tipo == TipoObjetivo.TODOS_LOS_ENEMIGOS) {
            objetivos.addAll(getEnemigosVivos());
        } else if (tipo == TipoObjetivo.TODOS_LOS_ALIADOS) {
            objetivos.addAll(party.getPersonajesVivos());
        } else {
            throw new IllegalStateException("Esta habilidad necesita que se elija un objetivo.");
        }
        ejecutarHabilidad(objetivos);
    }

    private void ejecutarHabilidad(List<Entidad> objetivos) {
        verificarTurnoDeHeroe();
        if (!puedeUsarHabilidad()) {
            throw new IllegalStateException(getEntidadActual().getNombre() + " no tiene maná suficiente.");
        }
        Entidad lanzador = getEntidadActual();
        ContextoAccion contexto = new ContextoAccion(lanzador, objetivos, null, lanzador.getHabilidad());
        ejecutarAccion(new AccionHabilidad(contexto));
    }

    public void usarConsumible(Consumible item, Entidad objetivo) {
        verificarTurnoDeHeroe();
        ContextoAccion contexto = new ContextoAccion(getEntidadActual(), objetivo, item, null);
        ejecutarAccion(new AccionConsumible(contexto, party.getInventario()));
    }


    // --- TURNO ENEMIGO ---
    public void ejecutarTurnoEnemigo() {
        if (finalizada || !esTurnoDeEnemigo()) {
            throw new IllegalStateException("No es el turno de un enemigo.");
        }
        Enemigo enemigo = (Enemigo) getEntidadActual();
        ejecutarAccion(enemigo.decidirAccion(party));
    }


    // --- CONSULTAS ---
    public boolean esTurnoDeEnemigo() {return enemigos.contains(gestor.obtenerEntidadActual());}
    public boolean estaFinalizada() {return finalizada;}
    public boolean hayVictoria() {return finalizada && gestor.enemigosDerrotados();}
    public boolean esJefeFinal() {
        return jefeFinal;
    }

    // para que el controlador habilite o no el botón de habilidad (si no alcanza el maná, la acción perdería el turno)
    public boolean puedeUsarHabilidad() {
        Entidad actual = getEntidadActual();
        return actual.tieneSuficienteMana(actual.getHabilidad().getCostoMana());
    }


    // --- PROCESAR ---
    @Override
    public void procesarNodo(EscenarioDeNodos escenario) {
        iniciarBatalla();
        escenario.alEntrarEnBatalla(this);
    }


    // --- GETTERS ---
    public Party getParty() {return party;}
    public Entidad getEntidadActual() {return gestor.obtenerEntidadActual();}
    public Recompensa getRecompensa() {return recompensa;}
    public List<Enemigo> getEnemigos() {return enemigos;}
    public List<Personaje> getSubieronDeNivel() {return subieronDeNivel;}
    public GestorDeTurnos getGestor() {
        return gestor;
    }
    public List<String> getOrdenTurnos() {
        return gestor.getOrdenTurnos();
    }
    public List<Enemigo> getEnemigosVivos() {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) {
            if (e.estaVivo()) vivos.add(e);
        }
        return vivos;
    }
}
