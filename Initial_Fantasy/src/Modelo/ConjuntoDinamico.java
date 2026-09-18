package Modelo;

public class ConjuntoDinamico implements ConjuntoTDA {
    class Nodo {
        int info;
        Nodo sig;
    }
    Nodo c;

    @Override
    public void InicializarConjunto() {
        c = null;
    }

    @Override
    public void Agregar(int valor) {
        if(!Pertenece(valor)){
            Nodo nuevo = new Nodo();
            nuevo.info = valor;
            nuevo.sig = c;
            c = nuevo;
        }
    }

    @Override
    public void Sacar(int valor) {
        if(c != null){
            if(c.info == valor){
                c = c.sig;
            }
            else{
                Nodo aux = c;
                while(aux.sig != null && aux.sig.info != valor){
                    aux = aux.sig;
                }
                if(aux.sig != null){
                    aux.sig = aux.sig.sig;
                }
            }
        }
    }

    @Override
    public int Elegir() {
        return c.info;
    }

    @Override
    public boolean Pertenece(int valor) {
         Nodo aux = c;
         while(aux != null && aux.info != valor){
             aux = aux.sig;
         }
         return (aux != null);
    }

    @Override
    public boolean ConjuntoVacio() {
        return (c==null);
    }
}
