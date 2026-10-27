package modelo.habilidades.enemigas;

import modelo.acciones.ContextoAccion;
import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.habilidades.TipoObjetivo;

public class HabilidadMiniJefe extends Habilidad {

    public HabilidadMiniJefe() {
        super("Sangre Caliente",50, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // pierde la paciencia, duplicando asi su defensa y ataque
        int aumentoAtaque = lanzador.getAtaqueActual();
        int aumentoDefensa = lanzador.getDefensaActual();
        lanzador.modificarAtaque(aumentoAtaque);
        lanzador.modificarDefensa(aumentoDefensa);
        }
    }
