package Modelo;

public interface GrafoTDA {

    public void InicializarGrafo();

    public void AgregarVertice(int v);

    public void EliminarVertice(int v);

    public void AgregarArista(int v1, int v2, int p);

    public void EliminarArista(int v1, int v2);

    public int PesoArista(int v1,int v2);

    public ConjuntoTDA Vertices();

    public boolean ExisteArista(int v1, int v2);

    public ConjuntoTDA Vecinos(int v);

    public boolean EsBidireccional(int v1,int v2);
}
