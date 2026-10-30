package utils;

import java.util.ArrayList;
import java.util.List;

public class BitacoraBatalla {

    private static final List<String> pendientes = new ArrayList<>();

    public static void registrar(String mensaje) {
        pendientes.add(mensaje);
    }

    // devuelve los mensajes nuevos desde la última vez y los saca de la cola
    public static List<String> retirarPendientes() {
        List<String> copia = new ArrayList<>(pendientes);
        pendientes.clear();
        return copia;
    }
    
    public static void limpiar() {
        pendientes.clear();
    }

    public static void limpiar() {
        pendientes.clear();
    }
}