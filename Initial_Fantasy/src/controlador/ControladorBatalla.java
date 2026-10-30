package controlador;

import dto.*;
import modelo.consumibles.Consumible;
import modelo.entidades.Entidad;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.personajes.Personaje;
import modelo.batalla.Batalla;
import modelo.habilidades.Habilidad;
import vista.pantallas.PantallaBatalla;
import vista.pantallas.PantallaDerrota;
import vista.pantallas.PantallaRecompensa;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ControladorBatalla {
    // tiempo de espera entre turnos enemigos
    private static final int PAUSA_TURNO_ENEMIGO_MS = 900;

    // qué acción eligió el jugador y está esperando que le elijan objetivo
    private enum AccionPendiente {ATACAR, HABILIDAD, CONSUMIBLE}
    private final Batalla batalla;
    private final PantallaBatalla vista;
    private Runnable alVolverAlMapa;
    private Runnable alReiniciarJuego;
    private Runnable alGanarJuego;

    private FaseBatalla fase = FaseBatalla.ELIGIENDO_ACCION;
    private AccionPendiente accionPendiente;
    private String consumiblePendiente;
    private String mensajeInfo = "Selecciona una accion.";

    public ControladorBatalla(Batalla batalla, PantallaBatalla vista, Runnable alVolverAlMapa, Runnable alReiniciarJuego,Runnable alGanarJuego) {
        this.batalla = batalla;
        this.vista = vista;
        this.alVolverAlMapa = alVolverAlMapa;
        this.alReiniciarJuego = alReiniciarJuego;
        this.alGanarJuego = alGanarJuego;
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
        mensajeInfo = "Selecciona un enemigo para atacar.";
        cambiarFase(FaseBatalla.ELIGIENDO_ENEMIGO);
    }

    public void elegirDefender() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION) return;
        batalla.defender();
        pausarYResolver();
    }

    public void elegirHabilidad() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION
                || !batalla.puedeUsarHabilidad()) {
            return;
        }

        Habilidad habilidad = batalla.getEntidadActual().getHabilidad();

        switch (habilidad.getTipoObjetivo()) {

            case UN_ENEMIGO:
                accionPendiente = AccionPendiente.HABILIDAD;

                mensajeInfo = habilidad.getNombre()
                        + "\n\n"
                        + habilidad.getDescripcion()
                        + "\n\nSelecciona un enemigo como objetivo."
                        + "\nCosto de maná: " + habilidad.getCostoMana();

                cambiarFase(FaseBatalla.ELIGIENDO_ENEMIGO);
                break;

            case UN_ALIADO:
                accionPendiente = AccionPendiente.HABILIDAD;

                mensajeInfo = habilidad.getNombre()
                        + "\n\n"
                        + habilidad.getDescripcion()
                        + "\n\nSelecciona un aliado como objetivo."
                        + "\nCosto de maná: " + habilidad.getCostoMana();

                cambiarFase(FaseBatalla.ELIGIENDO_ALIADO);
                break;

            default:
                mensajeInfo = habilidad.getNombre()
                        + "\n\n"
                        + habilidad.getDescripcion();

                batalla.usarHabilidad();
                pausarYResolver();
                break;
        }
    }

    public void elegirConsumible() {
        if (fase != FaseBatalla.ELIGIENDO_ACCION || batalla.getParty().getInventario().estaVacio()) return;
        accionPendiente = AccionPendiente.CONSUMIBLE;
        mensajeInfo = "Selecciona un consumible para ver sus efectos y utilizarlo.";
        cambiarFase(FaseBatalla.ELIGIENDO_CONSUMIBLE);
    }


    // --- 2) EL JUGADOR ELIGE UN CONSUMIBLE DEL INVENTARIO ---
    public void elegirConsumible(String nombreConsumible) {
        if (fase != FaseBatalla.ELIGIENDO_CONSUMIBLE) return;
        consumiblePendiente = nombreConsumible;
        mensajeInfo = "Consumible seleccionado: "
                + nombreConsumible
                + "\n\nSelecciona un aliado para utilizarlo.";
        cambiarFase(FaseBatalla.ELIGIENDO_ALIADO);
    }


    // --- 3) EL JUGADOR ELIGE A QUIEN (clic en un sprite; el índice es la posición en la lista del DTO) ---
    // los clics fuera de fase o sobre alguien derrotado se ignoran: son parte normal de usar la pantalla
    public void elegirEnemigo(int indiceEnemigo) {
        if (fase != FaseBatalla.ELIGIENDO_ENEMIGO || !enemigo(indiceEnemigo).estaVivo()) return;

        if (accionPendiente == AccionPendiente.ATACAR) {

            Entidad atacante = batalla.getEntidadActual();

            mensajeInfo = atacante.getNombre()
                    + " ataca con "
                    + atacante.getAtaqueActual()
                    + " de ATQ.";

            batalla.atacar(enemigo(indiceEnemigo));

        } else {

            Habilidad habilidad = batalla.getEntidadActual().getHabilidad();

            mensajeInfo = habilidad.getNombre()
                    + "\n"
                    + habilidad.getDescripcion();

            batalla.usarHabilidad(enemigo(indiceEnemigo));
        }

        pausarYResolver();
    }

    public void elegirAliado(int indiceAliado) {
        if (fase != FaseBatalla.ELIGIENDO_ALIADO
                || !aliado(indiceAliado).estaVivo()) {
            return;
        }

        Entidad objetivo = aliado(indiceAliado);

        if (accionPendiente == AccionPendiente.CONSUMIBLE) {

            mensajeInfo = "Usas "
                    + consumiblePendiente
                    + " sobre "
                    + objetivo.getNombre()
                    + ".";

            batalla.usarConsumible(
                    buscarConsumible(consumiblePendiente),
                    objetivo
            );

        } else if (accionPendiente == AccionPendiente.HABILIDAD) {

            Habilidad habilidad = batalla.getEntidadActual().getHabilidad();

            mensajeInfo = batalla.getEntidadActual().getNombre()
                    + " utiliza "
                    + habilidad.getNombre()
                    + " sobre "
                    + objetivo.getNombre()
                    + ".\n\n"
                    + habilidad.getDescripcion();

            batalla.usarHabilidad(objetivo);
        }

        pausarYResolver();
    }

    // para cancelar la accion
    public void cancelar() {
        boolean eligiendo = fase == FaseBatalla.ELIGIENDO_ENEMIGO
                || fase == FaseBatalla.ELIGIENDO_ALIADO
                || fase == FaseBatalla.ELIGIENDO_CONSUMIBLE;
        if (!eligiendo) return;

        limpiarSeleccion();
        mensajeInfo = "Selecciona una accion.";
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

            // Preguntamos a la Batalla/Gestor quién fue aniquilado
            if (batalla.hayVictoria()) {
                procesarVictoria(); // Ganaron los héroes
            } else {
                procesarDerrota(); // ¡GAME OVER!
            }

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
            pausarYResolver();
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

        Entidad entidadActual = batalla.getEntidadActual();
        List<String> ordenTurnos = batalla.getOrdenTurnos();

        return new EstadoBatallaDTO(
                convertirPersonajes(equipo),
                convertirEnemigos(enemigos),
                convertirConsumibles(consumibles),
                fase,
                convertirEntidad(entidadActual),
                mensajeInfo,
                ordenTurnos
        );
    }

    // Llama a este metodo cuando detectes que la batalla se ganó
    private void procesarVictoria() {

        // Si es el jefe final, termina el juego.
        if (batalla.esJefeFinal()) {
            if (alGanarJuego != null) {
                alGanarJuego.run();
            }
            return;
        }

        // Si es una batalla normal, mostramos las recompensas.
        int oroGanado = batalla.getRecompensa().getOro();
        double xpGanada = batalla.getRecompensa().getExperiencia();
        List<String> mensajesLevelUp = new ArrayList<>();
        for (Personaje p : batalla.getSubieronDeNivel()) {
            mensajesLevelUp.add(p.getNombre() + " subió al nivel " + p.getNivel() + "!");
        }

        JFrame ventanaPadre =
                (JFrame) SwingUtilities.getWindowAncestor(vista);

        PantallaRecompensa modal = new PantallaRecompensa(
                ventanaPadre,
                xpGanada,
                oroGanado,
                mensajesLevelUp,
                alVolverAlMapa
        );

        modal.setVisible(true);
    }

    private void procesarDerrota() {
        if (alReiniciarJuego == null) return;

        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(vista);

        PantallaDerrota modal = new PantallaDerrota(ventanaPadre, () -> {
            alReiniciarJuego.run();
        });
        modal.setVisible(true);
    }

    private EntidadDTO convertirEntidad(Entidad entidad){
        // GUARD CLAUSE: Si la entidad es null, retornamos null para evitar el crash
        if (entidad == null) {
            return null;
        }

        return new EntidadDTO(
                entidad.getNombre(),
                entidad.getNivel(),
                entidad.getVidaMaxima(),
                entidad.getVidaActual(),
                entidad.getManaMaximo(),
                entidad.getManaActual(),
                entidad.getAtaqueBase(),
                entidad.getAtaqueActual(),
                entidad.getDefensaBase(),
                entidad.getDefensaActual(),
                entidad.isDefendiendo(),
                entidad.getVelocidadBase(),
                entidad.getVelocidadActual(),
                entidad.getRutaSprite()
        );
    }

    private List<PersonajeDTO> convertirPersonajes(List<Personaje> personajes) {
        List<PersonajeDTO> personajesConvertidos = new ArrayList<>();
        for (Personaje p : personajes) {
            personajesConvertidos.add(new PersonajeDTO(
                    p.getNombre(), p.getNivel(), p.getVidaMaxima(), p.getVidaActual(),
                    p.getManaMaximo(), p.getManaActual(), p.getAtaqueBase(), p.getAtaqueActual(), p.getDefensaBase(),
                    p.getDefensaActual(), p.isDefendiendo(), p.getVelocidadBase(), p.getVelocidadActual(),
                    p.getRutaSprite(), p.getExperienciaActual(), p.getExperienciaRequerida()
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
                    e.getRutaSprite(), e.getOroOtorgado(), e.getExperienciaOtorgada()
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

    private void pausarYResolver() {
        limpiarSeleccion();
        cambiarFase(FaseBatalla.MOSTRANDO_RESULTADO); // Refresca la vista y bloquea botones

        // Pausamos 1.5 segundos (1500 ms) para que el jugador lea el mensaje
        Timer pausa = new Timer(2000, e -> {
            resolverTurno();
        });
        pausa.setRepeats(false);
        pausa.start();
    }
}

