package utils;
import java.util.ArrayList;
import java.util.List;

public class BitacoraBatalla {

    private static final List<String> mensajes = new ArrayList<>();

    public static void registrar(String mensaje) {
        mensajes.add(mensaje);
        if (mensajes.size() > 5) {
            mensajes.remove(0);
        }
    }

    public static String getUltimoMensaje() {
        if (mensajes.isEmpty()) return "Iniciando batalla...";
        return mensajes.get(mensajes.size() - 1);
    }
}
