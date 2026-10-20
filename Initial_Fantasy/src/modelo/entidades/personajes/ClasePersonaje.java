package modelo.entidades.personajes;

public enum ClasePersonaje {
    GUERRERO {
        @Override
        public Personaje crear(String nombre) {return new Guerrero(nombre);}
    },
    MAGO {
        @Override
        public Personaje crear(String nombre) {return new Mago(nombre);}
    },
    ARQUERO {
        @Override
        public Personaje crear(String nombre) {return new Arquero(nombre);}
    },
    CURANDERO {
        @Override
        public Personaje crear(String nombre) {return new Curandero(nombre);}
    },
    TANQUE {
        @Override
        public Personaje crear(String nombre) {return new Tanque(nombre);}
    };

    public abstract Personaje crear(String nombre);
}