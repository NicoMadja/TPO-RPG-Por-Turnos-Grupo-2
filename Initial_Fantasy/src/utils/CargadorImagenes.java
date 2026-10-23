package utils;

import java.net.URL;
import javax.swing.ImageIcon;

public final class CargadorImagenes {

    private CargadorImagenes() {
    }

    public static ImageIcon cargar(String rutaCompleta) {
        // Busca el recurso directamente en el classpath usando la ruta exacta que ya trae el DTO
        URL url = CargadorImagenes.class.getResource(rutaCompleta);

        if (url != null) {
            return new ImageIcon(url);
        }

        System.err.println("Error: No se encontró la imagen en -> " + rutaCompleta);
        return null;
    }
}