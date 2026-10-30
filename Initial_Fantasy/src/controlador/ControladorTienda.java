package controlador;

import dto.ConsumibleDTO;
import modelo.consumibles.Consumible;
import modelo.tienda.Tienda;
import vista.pantallas.PantallaTienda;
import java.util.ArrayList;
import java.util.List;

public class ControladorTienda {
    private Tienda modeloTienda;
    private PantallaTienda vistaTienda;
    private Runnable alSalirAlMapa;

    public ControladorTienda(Tienda modeloTienda, PantallaTienda vistaTienda, Runnable alSalirAlMapa) {
        this.modeloTienda = modeloTienda;
        this.vistaTienda = vistaTienda;
        this.alSalirAlMapa = alSalirAlMapa;
    }

    public void iniciar() {
        vistaTienda.mostrarMensaje("");
        actualizarPantalla();
    }

    // EVENTO: La vista nos manda el nombre del ítem clickeado
    public void procesarCompra(String nombreItem) {
        // Buscamos cuál es el Consumible real dentro del catálogo del modelo
        Consumible itemABuscar = null;
        for (Consumible c : modeloTienda.getCatalogo()) {
            if (c.getNombre().equals(nombreItem)) {
                itemABuscar = c;
                break;
            }
        }

        if (itemABuscar != null) {
            boolean comprado = modeloTienda.comprarObjeto(itemABuscar);
            actualizarPantalla();
            vistaTienda.mostrarMensaje(comprado ? "Compraste " + itemABuscar.getNombre() + "." : "No tenés oro suficiente para " + itemABuscar.getNombre() + ".");
        }
    }

    public void salir() {
        if (alSalirAlMapa != null) {
            alSalirAlMapa.run();
        }
    }

    private void actualizarPantalla() {
        // Obtenemos el oro y el inventario (ajusta getOro() o getMonedero().getOro() según tu Party)
        int oroActual = modeloTienda.getParty().getMonedero();

        // Convertimos las listas reales a DTOs
        List<ConsumibleDTO> catalogoDTO = armarDTOs(modeloTienda.getCatalogo());
        List<ConsumibleDTO> inventarioDTO = armarDTOs(modeloTienda.getParty().getInventario().getItems());

        vistaTienda.actualizarDatos(oroActual, catalogoDTO, inventarioDTO);
    }

    // Metodo auxiliar para traducir el modelo a DTO
    private List<ConsumibleDTO> armarDTOs(List<Consumible> itemsModelo) {
        List<ConsumibleDTO> dtos = new ArrayList<>();
        for (Consumible c : itemsModelo) {
            // AHORA PASAMOS LA RUTA REAL DEL SPRITE
            dtos.add(new ConsumibleDTO(c.getNombre(), c.getPrecio(), c.getRutaSprite()));
        }
        return dtos;
    }
}