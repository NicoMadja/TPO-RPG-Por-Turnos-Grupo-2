/*
package controlador;

import dto.ConsumibleDTO;
import dto.EnemigoDTO;
import dto.EstadoBatallaDTO;
import dto.PersonajeDTO;
import dto.FaseBatalla;
import modelo.consumibles.Consumible;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.personajes.Personaje;
import modelo.mapa.nodos.Batalla;
import vista.pantallas.PantallaBatalla;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class ControladorBatalla {
    // qué acción eligió el jugador y está esperando que le elijan objetivo
    private enum AccionPendiente {ATACAR, HABILIDAD, CONSUMIBLE}

    private final Batalla batalla;
    private final PantallaBatalla vista;

    // tiempo de espera entre turnos enemigos
    private static final int PAUSA_TURNO_ENEMIGO_MS = 900;

    // lo que el controlador recuerda entre un clic y el siguiente
    private FaseBatalla fase = FaseBatalla.ELIGIENDO_ACCION;
    private AccionPendiente accionPendiente;
    private String consumiblePendiente;

    public ControladorBatalla(Batalla batalla, PantallaBatalla vista) {
        this.batalla = batalla;
        this.vista = vista;
    }


    // --- INICIO ---
    // se llama una vez, con la batalla ya iniciada: si el primer turno es de un enemigo, juega antes que el jugador
    public void iniciar() {
        resolverTurno();
    }


    // --- 1) EL JUGADOR ELIGE UNA ACCION (botones del menú) ---
    public void elegirAtacar() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION) return;
        accionPendiente = AccionPendiente.ATACAR;
        cambiarFase(FaseBatalla.ELIGIENDO_ENEMIGO);
    }

    public void elegirDefender() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION) return;
        batalla.defender();
        resolverTurno();
    }

    public void elegirHabilidad() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION || !batalla.puedeUsarHabilidad()) return;

        switch (batalla.getEntidadActual().getHabilidad().getTipoObjetivo()) {
            case UN_ENEMIGO:
                accionPendiente = AccionPendiente.HABILIDAD;
                cambiarFase(FaseBatalla.ELIGIENDO_ENEMIGO);
                break;
            case UN_ALIADO:
                accionPendiente = AccionPendiente.HABILIDAD;
                cambiarFase(FaseBatalla.ELIGIENDO_ALIADO);
                break;
            default: // habilidad de grupo: no hay nada que elegir
                batalla.usarHabilidad();
                resolverTurno();
        }
    }

    public void elegirConsumible() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION || batalla.getParty().getInventario().estaVacio()) return;
        accionPendiente = AccionPendiente.CONSUMIBLE;
        cambiarFase(FaseBatalla.ELIGIENDO_CONSUMIBLE);
    }


    // --- 2) EL JUGADOR ELIGE UN CONSUMIBLE DEL INVENTARIO ---
    public void elegirConsumible(String nombreConsumible) {
        if (fase != FaseBatalla.ELIGIENDO_CONSUMIBLE) return;
        consumiblePendiente = nombreConsumible;
        cambiarFase(FaseBatalla.ELIGIENDO_ALIADO);
    }


    // --- 3) EL JUGADOR ELIGE A QUIEN (clic en un sprite; el índice es la posición en la lista del DTO) ---
    // los clics fuera de fase o sobre alguien derrotado se ignoran: son parte normal de usar la pantalla
    public void elegirEnemigo(int indiceEnemigo) {
        if (fase != FaseBatalla.ELIGIENDO_ENEMIGO || !enemigo(indiceEnemigo).estaVivo()) return;

        if (accionPendiente == AccionPendiente.ATACAR) {
            batalla.atacar(enemigo(indiceEnemigo));
        } else {
            batalla.usarHabilidad(enemigo(indiceEnemigo));
        }
        resolverTurno();
    }

    public void elegirAliado(int indiceAliado) {
        if (fase != FaseBatalla.ELIGIENDO_ALIADO || !aliado(indiceAliado).estaVivo()) return;

        if (accionPendiente == AccionPendiente.CONSUMIBLE) {
            batalla.usarConsumible(buscarConsumible(consumiblePendiente), aliado(indiceAliado));
        } else {
            batalla.usarHabilidad(aliado(indiceAliado));
        }
        resolverTurno();
    }


    // --- ARREPENTIRSE: vuelve al menú sin gastar el turno ---
    public void cancelar() {
        boolean eligiendo = fase == FaseBatalla.ELIGIENDO_ENEMIGO
                || fase == FaseBatalla.ELIGIENDO_ALIADO
                || fase == FaseBatalla.ELIGIENDO_CONSUMIBLE;
        if (!eligiendo) return;

        limpiarSeleccion();
        cambiarFase(FaseBatalla.ELIGIENDO_ACCION);
    }

    // para que la vista habilite o no el botón de habilidad
    public boolean puedeUsarHabilidad() {
        return batalla.puedeUsarHabilidad();
    }


    // --- DESPUES DE CADA ACCION ---
    // define qué sigue: si terminó la batalla, si juega un enemigo (con pausa) o si le toca al jugador
    private void resolverTurno() {
        limpiarSeleccion();

        if (batalla.estaFinalizada()) {
            cambiarFase(FaseBatalla.FINALIZADA);
        } else if (batalla.esTurnoDeEnemigo()) {
            cambiarFase(FaseBatalla.TURNO_ENEMIGO);
            programarTurnoEnemigo();
        } else {
            cambiarFase(FaseBatalla.ELIGIENDO_ACCION);
        }
    }

    // juega UN turno enemigo después de la pausa y vuelve a resolverTurno(), que programa el siguiente si también es de un enemigo
    private void programarTurnoEnemigo() {
        Timer pausa = new Timer(PAUSA_TURNO_ENEMIGO_MS, e -> {
            batalla.ejecutarTurnoEnemigo();
            resolverTurno();
        });
        pausa.setRepeats(false);
        pausa.start();
    }

    private void limpiarSeleccion() {
        accionPendiente = null;
        consumiblePendiente = null;
    }

    private void cambiarFase(FaseBatalla nuevaFase) {
        fase = nuevaFase;
        refrescarVista();
    }

    // --- DE POSICION / NOMBRE A OBJETO DEL MODELO ---
    private Enemigo enemigo(int indice) {
        return batalla.getEnemigos().get(indice);
    }

    private Personaje aliado(int indice) {
        return batalla.getParty().getPersonajes().get(indice);
    }

    // el DTO agrupa los consumibles repetidos por nombre: se usa el primero del inventario que coincida
    private Consumible buscarConsumible(String nombre) {
        for (Consumible c : batalla.getParty().getInventario().getItems()) {
            if (c.getNombre().equals(nombre)) return c;
        }
        throw new IllegalArgumentException("No hay \"" + nombre + "\" en el inventario.");
    }


    // --- VISTA ---
    public void refrescarVista() {
        vista.actualizar(crearEstadoBatallaDTO());
    }

    private EstadoBatallaDTO crearEstadoBatallaDTO() {
        List<Personaje> equipo = batalla.getParty().getPersonajes();
        List<Enemigo> enemigos = batalla.getEnemigos();
        List<Consumible> consumibles = batalla.getParty().getInventario().getItems();

        return new EstadoBatallaDTO(convertirPersonajes(equipo), convertirEnemigos(enemigos), convertirConsumibles(consumibles), fase);
    }

    private List<PersonajeDTO> convertirPersonajes(List<Personaje> personajes) {
        List<PersonajeDTO> personajesConvertidos = new ArrayList<>();
        for (Personaje p : personajes) {
            personajesConvertidos.add(new PersonajeDTO(
                    p.getNombre(), p.getNivel(), p.getVidaMaxima(), p.getVidaActual(),
                    p.getManaMaximo(), p.getManaActual(), p.getAtaqueBase(), p.getAtaqueActual(), p.getDefensaBase(),
                    p.getDefensaActual(), p.isDefendiendo(), p.getVelocidadBase(), p.getVelocidadActual(),
                    p.getExperienciaActual(), p.getExperienciaRequerida(), p.getRutaSprite()
            ));
        }
        return personajesConvertidos;
    }

    private List<EnemigoDTO> convertirEnemigos(List<Enemigo> enemigos) {
        List<EnemigoDTO> enemigosConvertidos = new ArrayList<>();
        for (Enemigo e : enemigos) {
            enemigosConvertidos.add(new EnemigoDTO(
                    e.getNombre(), e.getNivel(), e.getVidaMaxima(), e.getVidaActual(),
                    e.getManaMaximo(), e.getManaActual(), e.getAtaqueBase(), e.getAtaqueActual(), e.getDefensaBase(),
                    e.getDefensaActual(), e.isDefendiendo(), e.getVelocidadBase(), e.getVelocidadActual(),
                    e.getExperienciaOtorgada(), e.getOroOtorgado(), e.getRutaSprite()
            ));
        }
        return enemigosConvertidos;
    }

    private List<ConsumibleDTO> convertirConsumibles(List<Consumible> consumibles) {
        List<ConsumibleDTO> consumiblesConvertidos = new ArrayList<>();
        for (Consumible c : consumibles) {
            consumiblesConvertidos.add(new ConsumibleDTO(
                    c.getNombre(), c.getPrecio(), c.getRutaSprite()
            ));
        }
        return consumiblesConvertidos;
    }
}
 */
