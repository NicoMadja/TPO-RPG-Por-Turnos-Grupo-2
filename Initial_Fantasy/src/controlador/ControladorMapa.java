package controlador;

import dto.ConexionDTO;
import dto.NodoDTO;
import dto.PartidaDTO;
import dto.PersonajeDTO;
import modelo.entidades.personajes.Personaje;
import modelo.mundo.mapa.Mapa;
import modelo.mundo.Nodo;
import modelo.mundo.EscenarioDeNodos;
import modelo.party.Party;
import vista.pantallas.PantallaMapa;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.*;
import repositorioPartida.RepositorioPartida;
import javax.swing.JOptionPane;

public class ControladorMapa {
    private Mapa modeloMapa;
    private Map<Nodo, Point> coordenadasMapa;
    private PantallaMapa vistaMapa;
    private EscenarioDeNodos escenario;
    private Map<Integer, Nodo> diccionarioNodos;
    private Party partyActual;

    public ControladorMapa(Mapa modeloMapa, Map<Nodo, Point> coordenadasMapa, PantallaMapa vistaMapa, EscenarioDeNodos escenario, Party partyActual) {
        this.modeloMapa = modeloMapa;
        this.coordenadasMapa = coordenadasMapa;
        this.vistaMapa = vistaMapa;
        this.escenario = escenario;
        this.partyActual = partyActual;
        this.diccionarioNodos = new HashMap<>();

        vistaMapa.cambiarFondo(modeloMapa.getSprite());
        actualizarVista();
        vistaMapa.getBtnOpciones().addActionListener(e -> mostrarMenuOpciones());
    }

    private void actualizarVista() {
        List<NodoDTO> nodosDTO = new ArrayList<>();
        List<ConexionDTO> conexionesDTO = new ArrayList<>();
        diccionarioNodos.clear();

        List<Nodo> caminosDisponibles = modeloMapa.getCaminosDisponibles();
        Map<Nodo, NodoDTO> mapaAuxiliar = new HashMap<>();
        int idVisual = 0;

        for (Nodo nodo : modeloMapa.getNodos()) {
            NodoDTO dto = new NodoDTO();
            dto.setId(idVisual);
            dto.setTipo(nodo.getClass().getSimpleName().toUpperCase());
            dto.setEsActual(nodo == modeloMapa.getNodoActual());

            Point pos = coordenadasMapa.get(nodo);
            if (pos != null) {
                dto.setX(pos.x);
                dto.setY(pos.y);
            }

            dto.setHabilitado(caminosDisponibles.contains(nodo));

            nodosDTO.add(dto);
            diccionarioNodos.put(idVisual, nodo);
            mapaAuxiliar.put(nodo, dto);

            idVisual++;
        }

        for (Nodo origen : modeloMapa.getNodos()) {
            for (Nodo destino : modeloMapa.getSucesoresDe(origen)) {
                ConexionDTO conexion = new ConexionDTO();
                conexion.setOrigenX(mapaAuxiliar.get(origen).getX());
                conexion.setOrigenY(mapaAuxiliar.get(origen).getY());
                conexion.setDestinoX(mapaAuxiliar.get(destino).getX());
                conexion.setDestinoY(mapaAuxiliar.get(destino).getY());
                conexionesDTO.add(conexion);
            }
        }

        vistaMapa.cargarMapa(nodosDTO, conexionesDTO, e -> alHacerClicNodo(e));
    }

    private void mostrarMenuOpciones() {
        String[] opciones = {"Guardar Partida", "Volver al Menú", "Cancelar"};

        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Opciones de Juego",
                "Menú",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == 0) {
            guardarJuego();
        } else if (seleccion == 1) {
            int confirmar = JOptionPane.showConfirmDialog(
                    null,
                    "¿Seguro que quieres salir? El progreso no guardado se perderá.",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                escenario.alVolverAlMenu();
            }
        }
    }

    private void guardarJuego() {
        try {
            RepositorioPartida repositorio = new RepositorioPartida("partida_guardada.sav");

            // 1. traducimos los héroes actuales a DTOs
            List<PersonajeDTO> heroesDTO = new ArrayList<>();
            for (Personaje p : partyActual.getPersonajes()) {
                heroesDTO.add(new PersonajeDTO(
                        p.getNombre(), p.getNivel(), p.getVidaMaxima(), p.getVidaActual(),
                        p.getManaMaximo(), p.getManaActual(), p.getAtaqueBase(), p.getAtaqueActual(),
                        p.getDefensaBase(), p.getDefensaActual(), p.isDefendiendo(),
                        p.getVelocidadBase(), p.getVelocidadActual(), p.getRutaSprite(),
                        p.getExperienciaActual(), p.getExperienciaRequerida()
                ));
            }

            // 2. buscamos el ID del nodo visual
            int idNodoDondeEstoy = 0;
            for (Map.Entry<Integer, Nodo> entry : diccionarioNodos.entrySet()) {
                if (entry.getValue() == modeloMapa.getNodoActual()) {
                    idNodoDondeEstoy = entry.getKey();
                    break;
                }
            }

            // 3. empaquetamos y guardamos
            PartidaDTO estadoJuego = new PartidaDTO(
                    heroesDTO,
                    idNodoDondeEstoy,
                    partyActual.getInventario(),
                    partyActual.getMonedero()
            );
            estadoJuego.setIdMapa(modeloMapa.getIdMapa());
            repositorio.guardar(estadoJuego);

            JOptionPane.showMessageDialog(vistaMapa, "La partida se guardó correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaMapa, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alHacerClicNodo(ActionEvent e) {
        int idClickeado = Integer.parseInt(e.getActionCommand());
        Nodo nodoDestino = diccionarioNodos.get(idClickeado);

        modeloMapa.avanzarA(nodoDestino);
        actualizarVista();
        nodoDestino.procesarNodo(escenario);
    }
}