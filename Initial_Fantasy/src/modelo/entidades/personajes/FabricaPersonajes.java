package modelo.entidades.personajes;

public class FabricaPersonajes {

    // Metodo estatico que devuelve la instancia concreta
    public static Personaje crearPersonaje(String nombre) {
        if (nombre == null) {
            return null;
        }

        switch (nombre) {
            case "Guerrero":
                return new Guerrero(nombre);
            case "Arquero":
                return new Arquero(nombre);
            case "Tanque":
                return new Tanque(nombre);
            case "Curandero":
                return new Curandero(nombre);
            case "Mago":
                return new Mago(nombre);
            default:
                throw new IllegalArgumentException("Clase de personaje desconocida: " + nombre);
        }
    }
}