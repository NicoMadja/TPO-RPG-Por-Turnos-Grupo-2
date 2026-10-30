package excepciones;

public class PartidaNoEncontradaException extends Exception{
    private static final long serialVersionUID = 1L;

    public PartidaNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
