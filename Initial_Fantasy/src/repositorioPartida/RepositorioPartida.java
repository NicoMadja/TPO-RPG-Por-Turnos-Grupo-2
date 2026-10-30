package repositorioPartida;

import dto.PartidaDTO;
import excepciones.PartidaNoEncontradaException;

import java.io.*;

public class RepositorioPartida {
    private String rutaArchivo;

    public RepositorioPartida(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // Guarda el DTO
    public void guardar(PartidaDTO estadoJuego) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            salida.writeObject(estadoJuego);
        }
    }

    // Retorna el DTO
    public PartidaDTO cargar() throws IOException, ClassNotFoundException, PartidaNoEncontradaException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (PartidaDTO) entrada.readObject();
        } catch(FileNotFoundException e) {
            throw new PartidaNoEncontradaException("No se encontró la partida guardada.");
        }
    }

    public boolean existePartida() {
        File archivo = new File(rutaArchivo);
        return archivo.exists() && !archivo.isDirectory();
    }

    public void borrarPartida() {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            archivo.delete();
        }
    }
}