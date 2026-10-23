package controlador;

import dto.ConexionDTO;
import dto.NodoDTO;
import modelo.mundo.mapa.Mapa;
import modelo.mundo.Nodo;
import modelo.mundo.EscenarioDeNodos;
import vista.pantallas.PantallaMapa;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.*;

public class ControladorMapa {
    private Mapa modeloMapa;
    private Map<Nodo, Point> coordenadasMapa; // Guardamos el diseño visual
    private PantallaMapa vistaMapa;
    private EscenarioDeNodos escenario;
    private Map<Integer, Nodo> diccionarioNodos;

    // Modificamos el constructor para recibir las coordenadas
    public ControladorMapa(Mapa modeloMapa, Map<Nodo, Point> coordenadasMapa, PantallaMapa vistaMapa, EscenarioDeNodos escenario) {
        this.modeloMapa = modeloMapa;
        this.coordenadasMapa = coordenadasMapa;
        this.vistaMapa = vistaMapa;
        this.escenario = escenario;
        this.diccionarioNodos = new HashMap<>();

        actualizarVista();
    }

    private void actualizarVista() {
        List<NodoDTO> nodosDTO = new ArrayList<>();
        List<ConexionDTO> conexionesDTO = new ArrayList<>();
        diccionarioNodos.clear();

        List<Nodo> caminosDisponibles = modeloMapa.getCaminosDisponibles();
        Map<Nodo, NodoDTO> mapaAuxiliar = new HashMap<>();
        int idVisual = 0;

        // 1. Convertir los Nodos a DTOs
        for (Nodo nodo : modeloMapa.getNodos()) {
            NodoDTO dto = new NodoDTO();
            dto.setId(idVisual);
            dto.setTipo(nodo.getClass().getSimpleName().toUpperCase());
            dto.setEsActual(nodo == modeloMapa.getNodoActual());
            // Leemos la posición exacta que definió el Prefab para este nodo
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

        // 2. Trazar las líneas (conexiones)
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

    private void alHacerClicNodo(ActionEvent e) {
        int idClickeado = Integer.parseInt(e.getActionCommand());
        Nodo nodoDestino = diccionarioNodos.get(idClickeado);

        modeloMapa.avanzarA(nodoDestino);
        actualizarVista();
        nodoDestino.procesarNodo(escenario);
    }
}