package modelo.acciones;

import modelo.entidades.Entidad;

public class AccionAtaque extends Accion {

    @Override
    public void ejecutar(ContextoAccion contexto) {
        Entidad atacante = contexto.getOrigen();
        // sacamos al unico objetivo de la lista
        Entidad objetivo = contexto.getObjetivos().get(0);

        // repele el ataque básico si está defendido (si usó AccionDefensa)
        if (objetivo.isDefendiendo()) {
            System.out.println(objetivo.getNombre() + " está en postura defensiva y repelió completamente el ataque de " + atacante.getNombre() + "!");
            return; // Cortamos la ejecución acá. No hay cálculo de daño.
        }

        // si no está defendiendo
        int danioFinal = atacante.getAtaqueActual() - objetivo.getDefensaActual();
        if (danioFinal < 0) danioFinal = 0;

        objetivo.recibirDanio(danioFinal);
        System.out.println(atacante.getNombre() + " atacó a " + objetivo.getNombre() + " causando " + danioFinal + " de daño.");
    }
}
