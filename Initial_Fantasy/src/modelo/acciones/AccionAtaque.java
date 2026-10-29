package modelo.acciones;

import modelo.entidades.Entidad;

public class AccionAtaque extends Accion {

    public AccionAtaque(ContextoAccion contexto) {
        super(contexto);
    }

    @Override
    public void ejecutar() {
        Entidad atacante = contexto.getOrigen();
        if (contexto.getObjetivos().isEmpty()) return;
        Entidad objetivo = contexto.getObjetivos().get(0);

        // repele el ataque básico si está defendido (si usó AccionDefensa)
        if (objetivo.isDefendiendo()) {
            utils.BitacoraBatalla.registrar(objetivo.getNombre() + " está en postura defensiva y repelió completamente el ataque de " + atacante.getNombre() + "!");
            return;
        }

        // si no está defendiendo
        int danioFinal = atacante.getAtaqueActual() - objetivo.getDefensaActual();
        if (danioFinal < 0) danioFinal = 0;

        objetivo.recibirDanio(danioFinal);
        utils.BitacoraBatalla.registrar(atacante.getNombre() + " atacó a " + objetivo.getNombre() + " causando " + danioFinal + " de daño.");
    }
}
