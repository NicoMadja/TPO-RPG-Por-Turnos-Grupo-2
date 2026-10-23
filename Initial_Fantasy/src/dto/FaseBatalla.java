
package dto;

// en qué momento está la pantalla de batalla
public enum FaseBatalla {
    ELIGIENDO_ACCION,      // menú de acciones habilitado
    ELIGIENDO_ENEMIGO,     // el jugador tiene que hacer clic en un enemigo
    ELIGIENDO_ALIADO,      // el jugador tiene que hacer clic en un héroe
    ELIGIENDO_CONSUMIBLE,  // el jugador tiene que elegir un ítem del inventario
    TURNO_ENEMIGO,
    MOSTRANDO_RESULTADO,// juegan los enemigos, el jugador espera
    FINALIZADA             // terminó la batalla
}
