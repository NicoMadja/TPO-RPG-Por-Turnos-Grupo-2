/**package controlador;

import dto.ConexionDTO;
import dto.NodoDTO;
import modelo.mundo.mapa.Mapa;
import modelo.mundo.Nodo;
import modelo.mundo.EscenarioDeNodos;
import vista.pantallas.PantallaMapa;
import java.awt.event.ActionEvent;
import java.util.*;

public class ControladorMapa {
    private Mapa modeloMapa;
    private PantallaMapa vistaMapa;
    private EscenarioDeNodos escenario;
    private Map<Integer, Nodo> diccionarioNodos;

    public ControladorMapa(Mapa modeloMapa, PantallaMapa vistaMapa, EscenarioDeNodos escenario) {
        this.modeloMapa = modeloMapa;
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

        // 1. Convertir los Nodos de tu modelo a DTOs para la pantalla
        for (Nodo nodo : modeloMapa.getNodos()) {
            NodoDTO dto = new NodoDTO();
            dto.setId(idVisual);
            dto.setTipo(nodo.getClass().getSimpleName().toUpperCase());

            // --- ASIGNACIÓN DE COORDENADAS EXACTAS ---
            if (nodo == modeloMapa.getNodoInicial()) {
                // NODO INICIAL (Círculo morado abajo)
                dto.setX(890); dto.setY(900);
            }
            else if (nodo == modeloMapa.getNodoFinal()) {
                // JEFE FINAL (Castillo arriba)
                dto.setX(890); dto.setY(150);
            }
            else if (dto.getTipo().equals("BATALLA")) {
                if (idVisual == 1) { // Batalla 1 (Izquierda abajo)
                    dto.setX(620); dto.setY(780);
                } else if (idVisual == 2) { // Batalla 2 (Centro intersección)
                    dto.setX(890); dto.setY(630);
                } else { // Batalla 3 (Izquierda arriba)
                    dto.setX(680); dto.setY(320);
                }
            }
            else if (dto.getTipo().equals("TIENDA")) {
                // Tienda (Izquierda medio del lago)
                dto.setX(520); dto.setY(500);
            }
            else if (dto.getTipo().equals("CAMPAMENTO")) {
                // Campamento (Derecha medio del lago)
                dto.setX(1250); dto.setY(500);
            }
            // Habilita el botón SOLO si el nodo está en la lista de caminos disponibles
            dto.setHabilitado(caminosDisponibles.contains(nodo));

            nodosDTO.add(dto);
            diccionarioNodos.put(idVisual, nodo);
            mapaAuxiliar.put(nodo, dto);

            idVisual++;
        }

        // 2. Trazar las líneas (conexiones) entre los nodos
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

        // 3. Finalmente, le pasamos los datos listos a la vista para que los pinte
        vistaMapa.cargarMapa(nodosDTO, conexionesDTO, e -> alHacerClicNodo(e));
    }

    private void alHacerClicNodo(ActionEvent e) {
        int idClickeado = Integer.parseInt(e.getActionCommand());
        Nodo nodoDestino = diccionarioNodos.get(idClickeado);

        modeloMapa.avanzarA(nodoDestino);
        actualizarVista();
        nodoDestino.procesarNodo(escenario);
    }
}*/