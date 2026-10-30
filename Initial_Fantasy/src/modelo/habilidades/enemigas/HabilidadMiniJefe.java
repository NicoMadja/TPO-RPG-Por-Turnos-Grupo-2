package modelo.habilidades.enemigas;

import modelo.acciones.ContextoAccion;
import modelo.entidades.Entidad;
import modelo.habilidades.Habilidad;
import modelo.habilidades.TipoObjetivo;

public class HabilidadMiniJefe extends Habilidad {

    public HabilidadMiniJefe() {
        super("Irascible","Pierde la paciencia, aumentando asi su velocidad y ataque",50, TipoObjetivo.UN_ENEMIGO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();


        int cantidadAtaque = lanzador.getAtaqueActual() * 2;
        int cantidadVelocidad = lanzador.getAtaqueActual() * 2;
        System.out.println(lanzador.getAtaqueActual());
        lanzador.modificarAtaque(cantidadAtaque);{
            System.out.println("nombre del objetivo " + lanzador.getNombre());
            System.out.println(lanzador.getAtaqueActual());
        lanzador.modificarVelocidad(cantidadVelocidad);}
        }
    }

    /*public HabilidadCurandero() {
        super("Sanacion Localizada", 20, TipoObjetivo.UN_ALIADO);
    }

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad lanzador = contexto.getOrigen();

        // cura a un aliado el triple del ataque del curandero
        int cantidadCuracion = lanzador.getAtaqueActual() * 3;
        for (Entidad objetivo : contexto.getObjetivos()) {
            if (objetivo.estaVivo()) objetivo.recibirCura(cantidadCuracion);
        }
    }*/

