package utils;

import java.io.File;
import javax.swing.ImageIcon;

public final class CargadorImagenes {

    private CargadorImagenes() {
    }

    public static ImageIcon cargar(
            String nombreArchivo) {

        String[] rutas = {
                "src/recursos/sprites/" + nombreArchivo,
                "Initial_Fantasy/src/recursos/sprites/" + nombreArchivo
        };

        for (String ruta : rutas) {

            File archivo =
                new File(ruta);

            if (archivo.exists()) {
                return new ImageIcon(ruta);
            }
        }

        return null;
    }
}
