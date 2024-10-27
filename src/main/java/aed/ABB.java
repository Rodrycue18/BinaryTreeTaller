package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    Nodo raiz;
    int cardinal;
    int altura;
    private class Nodo {
        // Agregar atributos privados del Nodo
        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;

        // Crear Constructor del nodo
        Nodo(T v){
            this.valor = v;
            this.izq = null;
            this.der = null;
            this.padre = null;
        }
    }
    public ABB() {
        this.raiz = null;
        this.altura = 0;
        this.cardinal=0;
    }

    public int cardinal() {
        return this.cardinal;
    }

    public T minimo(){
        Nodo actual = raiz;
        while (actual.izq != null) {
            actual = actual.izq;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = raiz;

        while (actual.der != null) {
            actual = actual.der;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        if (!(pertenece(elem))) {
            Nodo nuevo = new Nodo(elem);
            Nodo actual = raiz;
            Nodo previo = actual;
            if(raiz == null){
                raiz = nuevo;
                // raiz.der = null;
                // raiz.izq = null;
                this.cardinal++;
                // return;
            }else{
                //nodo previo es el padre del nodo a ser insertado
                while (actual != null) {
                    previo = actual;
                    if(elem.compareTo(actual.valor) < 0){
                        actual = actual.izq;
                    }else{
                        if (elem.compareTo(actual.valor) > 0) {
                            actual = actual.der;
                        }
                    }
                }
                // agrego los valores al nodo nuevo y al padre del nodo nuevo
                if(elem.compareTo(previo.valor) < 0){
                    previo.izq = nuevo;
                    nuevo.padre = previo;
                    cardinal++;
                }else{
                    if (elem.compareTo(previo.valor) > 0) {
                        previo.der = nuevo;
                        nuevo.padre = previo;
                        cardinal++;
                    }
                }
            }
            
            
            
            
        }
    }

    public boolean pertenece(T elem){
        //TODO: NO ME GUSTA NADA MI CODIGO PERTENECE PODEMOS USAR LA MISMA LOGICA DE INSERTAR
        Nodo actual = raiz;
        boolean res = false;
        if(raiz == null){
            return res;
        }

        if (elem.compareTo(raiz.valor) < 0){
            // actual = actual.izq;
            while ((actual != null) && elem.compareTo(actual.valor) != 0 ) {
                if (elem.compareTo(actual.valor) < 0){
                    actual = actual.izq;
                }else{
                    if(elem.compareTo(actual.valor) > 0){
                        actual = actual.der;
                    }
                }
            }
        }else{
            if (elem.compareTo(raiz.valor) > 0){
                // actual = actual.der;
                while ((actual != null) && elem.compareTo(actual.valor) != 0 ) {
                    if (elem.compareTo(actual.valor) > 0){
                        actual = actual.der;
                    }else{
                        if(elem.compareTo(actual.valor) < 0){
                            actual = actual.izq;
                        }
                    }
                }    
            }
        }
        

        if (elem.compareTo(raiz.valor) == 0 ){ //|| elem.compareTo(actual.valor) == 0){
            res =  true;
        }else{
            if(actual== null){
                res = false;
            }else{
                if(elem.compareTo(actual.valor) == 0){
                    res = true;
                }
            }
            
        }
        return res;
    }

    public void eliminar(T elem){
        if (pertenece(elem)) {
            Nodo actual = raiz;
            Nodo previo = null;
            //Primer paso encontrar elemento a eliminar
            while (elem.compareTo(actual.valor) != 0){
                previo = actual;
                if(elem.compareTo(actual.valor) < 0){
                    actual = actual.izq;
                }else{
                    if (elem.compareTo(actual.valor) > 0) {
                        actual = actual.der;
                    }
                }
            }
            //caso donde tiene como maximo un hijo
            if(actual.izq == null || actual.der == null){
                Nodo nuevoActual;
                //Vemos que rama es null
                if(actual.izq == null){
                    nuevoActual = actual.der;
                }else{
                    nuevoActual = actual.izq;
                }
                //Chequeo si es la raiz la que queremos eliminar
                if(previo == null){
                    raiz = nuevoActual;
                    nuevoActual.padre = null;
                }else{
                    //Veo en que rama esta el nodo que quiero eliminar,comparando con el de su padre
                    if(actual == previo.izq){
                        previo.izq = nuevoActual;
                    }else{
                        previo.der = nuevoActual;
                    }
                }   
            }else{
                //Nodo tiene dos hijos
                Nodo aux = null;
                Nodo temporal = actual.der;
                //Busco sucesor
                while(temporal.izq != null){
                    aux = temporal;
                    temporal = temporal.izq;
                }
                //Si aux es null significa que el primer nodo de la derecha no tiene rama izquierda
                if(aux != null){
                    aux.izq = temporal.der;
                }else{
                    actual.der = temporal.der;
                }
                actual.valor = temporal.valor;

            }
            // // caso donde eliminamos la raiz PORQUE PARECE SER QUE SI SE ELIMINA LA RAIZ SE PONE RARO
            // if (actual.izq == null && actual.der== null){
            //     actual = null;
            // }else{
            //     //PREGUNTO SI TIENE ARBOL DERECHO
            //     if (actual.der != null){
            //         //encuentro sucesor osea el mas pequenio de todos los grandes
            //         Nodo sucesor = actual.der;
            //         while (sucesor.izq != null) {
            //             sucesor = sucesor.izq;
            //         }
            //         if(actual,valor.compareTo(raiz.valor)==0){}
            //         sucesor.padre.izq = null;
            //         sucesor.padre = actual.padre;
            //         sucesor.der = actual.der;
            //         sucesor.izq = actual.izq; 
            //     }else{
            //         //si solo tiene arbol izq, se trata solo de eliminar una hoja
            //         if(actual.izq != null && actual.der ==null){
            //             Nodo hijo = actual.izq;
            //             Nodo padre = actual.padre;
            //             hijo.padre = actual.padre;
            //             padre.izq = hijo;
            //         }
            //     }
            // }
            // //Ya estamos parados sobre el nodo a eliminar
            // //Caso 1 : nodo sin hijos
            // if (actual.izq == null && actual.der== null){
            //     actual = null; 
            // }else{
            //     //CASO 3: nodo con dos hijos , usamos a sucesor, primero vemos caso si tiene dos hijos
            // if (actual.der != null && actual.izq!=null){
            //     return;
            // }else{
            //         // Caso 2: nodo con solo un hijo usamos operador xor
            //         if (actual.izq == null ^ actual.der== null){
            //             if(actual.izq != null){
            //                 Nodo hijo = actual.izq;
            //                 Nodo padre = actual.padre;
            //                 hijo.padre = actual.padre;
            //                 padre.izq = hijo;
            //             }else{
            //                 if(actual.der!=null){
            //                     Nodo hijo = actual.der;
            //                     Nodo padre = actual.padre;
            //                     hijo.padre = actual.padre;
            //                     padre.der = hijo;
            //                 }
            //             }   
            //         }
            //     }
            // }
            
            
             
            // if()
            cardinal--;
        }
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
