package Modelo.Mapa;

public class GrafoDinamico implements GrafoTDA {

    class NodoGrafo {
        int nodo;
        NodoArista arista;
        NodoGrafo sigNodo;
    }

    class NodoArista {
        int etiqueta;
        NodoGrafo nodoDestino;
        NodoArista sigArista;
    }
    NodoGrafo origen;

    @Override
    public void InicializarGrafo() {
        origen = null;
    }

    @Override
    public void AgregarVertice(int v) {
        NodoGrafo aux = new NodoGrafo();
        aux.nodo = v;
        aux.arista = null;
        aux.sigNodo = origen;
        origen = aux;
    }

    private NodoGrafo Vert2Nodo(int v){
        NodoGrafo aux = origen;
        while(aux != null && aux.nodo != v){
            aux = aux.sigNodo;
        }
        return aux;
    }

    @Override
    public void EliminarVertice(int v) {
        if(origen.nodo == v){
            origen = origen.sigNodo;
        }
        NodoGrafo aux = origen;
        while(aux != null){
            this.EliminarAristaNodo(aux,v);
            if(aux.sigNodo != null && aux.sigNodo.nodo == v){
                aux.sigNodo = aux.sigNodo.sigNodo;
            }
            aux = aux.sigNodo;
        }
    }

    @Override
    public void AgregarArista(int v1, int v2, int p) {
        NodoGrafo n1 = Vert2Nodo(v1);
        NodoGrafo n2 = Vert2Nodo(v2);
        NodoArista aux = new NodoArista();
        aux.etiqueta = p;
        aux.nodoDestino = n2;
        aux.sigArista = n1.arista;
        n1.arista = aux;
    }

    private void EliminarAristaNodo(NodoGrafo nodo, int v){
        NodoArista aux = nodo.arista;
        if(aux != null){
           if(aux.nodoDestino.nodo == v){
               nodo.arista = aux.sigArista;
           }
           else{
               while(aux.sigArista != null && aux.sigArista.nodoDestino.nodo != v){
                   aux = aux.sigArista;
               }
               if(aux.sigArista != null){
                   aux.sigArista = aux.sigArista.sigArista;
               }
           }
        }
    }

    @Override
    public void EliminarArista(int v1, int v2) {
        NodoGrafo n1 = Vert2Nodo(v1);
        EliminarAristaNodo(n1,v2);
    }

    @Override
    public int PesoArista(int v1, int v2) {
        NodoGrafo n1 = Vert2Nodo(v1);
        NodoArista aux = n1.arista;
        while(aux.nodoDestino.nodo != v2){
            aux = aux.sigArista;
        }
        return aux.etiqueta;
    }

    @Override
    public ConjuntoTDA Vertices() {
        ConjuntoTDA c = new ConjuntoDinamico();
        c.InicializarConjunto();
        NodoGrafo aux = origen;
        while (aux != null){
            c.Agregar(aux.nodo);
            aux = aux.sigNodo;
        }
        return c;
    }

    @Override
    public boolean ExisteArista(int v1, int v2) {
        NodoGrafo n1 = Vert2Nodo(v1);
        NodoArista aux = n1.arista;
        while(aux != null && aux.nodoDestino.nodo != v2){
            aux = aux.sigArista;
        }
        return (aux != null);
    }

    public ConjuntoTDA Vecinos(int v){
        ConjuntoTDA c1 = Vertices();
        ConjuntoTDA c2 = new ConjuntoDinamico();
        while (!c1.ConjuntoVacio()){
            int p = PesoArista(v,c1.Elegir());
            if(p != 0){
                c2.Agregar(c1.Elegir());
            }
            c1.Sacar(c1.Elegir());
        }
        return c2;
    }

    @Override
    public boolean EsBidireccional(int v1, int v2) {
        int p1 = PesoArista(v1,v2);
        int p2 = PesoArista(v2,v1);
        if(p1 != 0 && p2 != 0){
            return true;
        }
        else{
            return false;
        }
    }
}
