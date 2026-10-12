package controlador;

import dto.EnemigoDTO;
import dto.EstadoBatallaDTO;
import dto.PersonajeDTO;
import modelo.entidades.enemigos.Enemigo;
import modelo.entidades.personajes.Personaje;
import modelo.mapa.nodos.Batalla;
import vista.pantallas.PantallaBatalla;
import java.util.ArrayList;
import java.util.List;

public class ControladorBatalla {
    private final Batalla batalla;
    private final PantallaBatalla vista;

    public ControladorBatalla(Batalla batalla, PantallaBatalla vista) {
        this.batalla = batalla;
        this.vista = vista;
    }

    public void atacar() {
        batalla.atacar();
        refrescarVista();
    }

    public void usarHabilidad() {
        batalla.usarHabilidad();
        refrescarVista();
    }

    public void defender() {
        batalla.defender();
        refrescarVista();
    }

    public void usarConsumible() {
        batalla.usarConsumible();
        refrescarVista();
    }

    public void refrescarVista() {
        vista.actualizar(crearEstadoBatallaDTO());
    }

    private EstadoBatallaDTO crearEstadoBatallaDTO() {
        List<Personaje> equipo = batalla.getParty().getPersonajes();
        List<Enemigo> enemigos = batalla.getEnemigos();

        List<String> spritesEquipo = ;
        List<String> spritesEnemigos = ;

        return new EstadoBatallaDTO(convertirPersonajes(equipo, spritesEquipo), convertirEnemigos(enemigos, spritesEnemigos));
    }

    private List<PersonajeDTO> convertirPersonajes(List<Personaje> personajes, List<String> sprites) {
        List<PersonajeDTO> personajesConvertidos = new ArrayList<>();
        int c = 0;
        for (Personaje p : personajes) {
            personajesConvertidos.add(new PersonajeDTO(
                    p.getNombre(), p.getNivel(), p.getVidaMaxima(), p.getVidaActual(),
                    p.getManaMaximo(), p.getManaActual(), p.getAtaqueBase(), p.getAtaqueActual(), p.getDefensaBase(),
                    p.getDefensaActual(), p.isDefendiendo(), p.getVelocidadBase(), p.getVelocidadActual(),
                    p.getExperienciaActual(), p.getExperienciaRequerida(), sprites.get(c)));
            c += 1;
        }
        return personajesConvertidos;
    }

    private List<EnemigoDTO> convertirEnemigos(List<Enemigo> enemigos, List<String> sprites) {
        List<EnemigoDTO> enemigosConvertidos = new ArrayList<>();
        int c = 0;
        for (Enemigo e : enemigos) {
            enemigosConvertidos.add(new EnemigoDTO(
                    e.getNombre(), e.getNivel(), e.getVidaMaxima(), e.getVidaActual(),
                    e.getManaMaximo(), e.getManaActual(), e.getAtaqueBase(), e.getAtaqueActual(), e.getDefensaBase(),
                    e.getDefensaActual(), e.isDefendiendo(), e.getVelocidadBase(), e.getVelocidadActual(),
                    e.getExperienciaOtorgada(), e.getOroOtorgado(), sprites.get(c)));
            c += 1;
        }
        return enemigosConvertidos;
    }
}
