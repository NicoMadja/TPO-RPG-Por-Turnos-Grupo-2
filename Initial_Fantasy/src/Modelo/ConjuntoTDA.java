package Modelo;

public interface ConjuntoTDA {

    public void InicializarConjunto();

    public void Agregar(int valor);

    public void Sacar(int valor);

    public int Elegir();

    public boolean Pertenece(int valor);

    public boolean ConjuntoVacio();
}
