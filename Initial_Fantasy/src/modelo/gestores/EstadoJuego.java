package modelo.gestores;

public enum EstadoJuego {
    SIN_INICIAR, // todavia no se creó la party
    EN_CURSO,    // partida en curso
    VICTORIA,    // se venció al jefe final
    DERROTA      // murieron todos los personajes
}