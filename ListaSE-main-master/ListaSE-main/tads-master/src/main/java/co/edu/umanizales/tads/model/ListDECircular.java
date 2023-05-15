package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.exception.ListDEEExceptionCircular;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor


public class ListDECircular {
    private NodeDE nextfirst;
    private NodeDE previouslast;

    public ListDECircular() {
        nextfirst = null;
        previouslast = null;
    }

/*
      ALGORITMO GET INTO=INGRESAR

    COMENZAMOS CREANDO UN MÉTODO LLAMADO "GETINTO" QUE TOMA UN PARÁMETRO DE ENTRADA DE TIPO ENTERO (X).
    CREAMOS UN NUEVO NODO LLAMADO "NODEDE".
    ASIGNAMOS EL VALOR DE X AL NUEVO NODO UTILIZANDO EL MÉTODO "SETDATA".
    VERIFICAMOS SI EL SIGUIENTE NODO EN LA LISTA (NEXTFIRST) ES NULO. SI ES ASÍ, ENTONCES LA LISTA ESTÁ VACÍA, Y REALIZAMOS LOS SIGUIENTES PASOS:
    A. ASIGNAMOS EL NUEVO NODO (NODEDE) COMO EL SIGUIENTE NODO (NEXTFIRST).
    B. HACEMOS QUE EL SIGUIENTE NODO DEL NUEVO NODO SEA EL MISMO NODO (NEXTFIRST).
    C. HACEMOS QUE EL NODO ANTERIOR DEL PRIMER NODO SEA EL NUEVO NODO (NODEDE).
    D. ESTABLECEMOS EL NUEVO NODO (NODEDE) COMO EL ÚLTIMO NODO DE LA LISTA (PREVIOUSLAST).
    SI LA LISTA NO ESTÁ VACÍA, ENTONCES REALIZAMOS LOS SIGUIENTES PASOS:
    A. HACEMOS QUE EL SIGUIENTE NODO DEL ÚLTIMO NODO (PREVIOUSLAST) SEA EL NUEVO NODO (NODEDE).
    B. HACEMOS QUE EL NODO ANTERIOR DEL NUEVO NODO (NODEDE) SEA EL ÚLTIMO NODO DE LA LISTA (PREVIOUSLAST).
    C. HACEMOS QUE EL SIGUIENTE NODO DEL NUEVO NODO (NODEDE) SEA EL PRIMER NODO DE LA LISTA (NEXTFIRST).
    D. HACEMOS QUE EL NODO ANTERIOR DEL PRIMER NODO (NEXTFIRST) SEA EL NUEVO NODO (NODEDE).
    E. ESTABLECEMOS EL NUEVO NODO (NODEDE) COMO EL ÚLTIMO NODO DE LA LISTA (PREVIOUSLAST).


 */
    public void getinto( Pet pet,int data) {
        NodeDE nodeDE = new NodeDE(pet);
        nodeDE.setData(nodeDE.getData());

        if (nextfirst == null) { // Si la lista está vacía
            nextfirst = nodeDE;
            nextfirst.setNext(nextfirst);
            nextfirst.setPrevious(nodeDE);
            previouslast = nodeDE;
        } else { // Si la lista no está vacía
            previouslast.setNext(nodeDE);
            nodeDE.setPrevious(previouslast);
            nodeDE.setNext(nextfirst);
            nextfirst.setPrevious(nodeDE);
            previouslast = nodeDE;
        }
    }

/*

    ALGORITMO MOSTRAR o LISTAR = SHOWList

    COMENZAMOS A CREAR NUESTRO MÉTODO COMO NOMBRE "SHOW", Y TENIENDO EN CUENTA QUE PUEDE LANZAR UNA EXCEPCIÓN DE TIPO
    "LISTDEEEXCEPTIONCIRCULAR".
    PRIMERO PREGUNTAMOS SI EL PRIMER NODO DE LA LISTA (nextfirst) ES NULO. SI LO ES, ENTONCES LA LISTA ESTÁ VACÍA Y NO
    TENEMOS NADA QUE MOSTRAR.

    SI EL PRIMER NODO NO ES NULO, CREAMOS UN NUEVO NODO LLAMADO "currentNode" Y LO INICIALIZAMOS CON EL VALOR DE nextfirst.
    ENTRAMOS EN UN BUCLE WHILE QUE SE EJECUTARÁ MIENTRAS EL NODO ACTUAL (currentNode) NO SEA EL SIGUIENTE DEL PRIMER NODO
    (nextfirst.getNext()).

    EN CADA ITERACIÓN DEL BUCLE, ACTUALIZAMOS EL NODO ACTUAL (currentNode) AL SIGUIENTE NODO (currentNode = currentNode.getNext()).
    SI EL BUCLE TERMINA, SIGNIFICA QUE HEMOS RECORRIDO TODA LA LISTA CIRCULAR Y VOLVIMOS AL PRIMER NODO. SIN EMBARGO, ESTO ES
    UN ERROR YA QUE LA LISTA DEBE SER CIRCULAR Y NO DEBE TERMINAR NUNCA. POR LO TANTO, LANZAMOS UNA EXCEPCIÓN DEL TIPO
    LISTDEEEXCEPTIONCIRCULAR CON EL VALOR DE currentNode.getData() CONVERTIDO A STRING COMO MENSAJE DE ERROR.
 */
    public void showList() throws ListDEEExceptionCircular {
        if (nextfirst == null) {
            // La lista está vacía
        } else {
            NodeDE currentNode = nextfirst;
            while (currentNode != nextfirst.getNext()) {
                currentNode = currentNode.getNext();
            }
            throw new ListDEEExceptionCircular(currentNode.getData() + "");
        }
    }
    /*
    ALGORITMO INSERTAR POSICION
    VERIFICAR QUE LA LISTA NO ESTÁ VACÍA. SI ESTÁ VACÍA, LANZAR UNA EXCEPCIÓN.
    CREAR UN NUEVO NODO CON EL VALOR DESEADO.
    VERIFICAR QUE LA POSICIÓN ES MAYOR QUE 0. SI NO LO ES, LANZAR UNA EXCEPCIÓN.
    SI LA POSICIÓN ES 1, INSERTAR EL NUEVO NODO AL PRINCIPIO DE LA LISTA.
    SI LA POSICIÓN NO ES 1, ENCONTRAR EL NODO EN LA POSICIÓN ANTERIOR A LA POSICIÓN DE INSERCIÓN.
    INSERTAR EL NUEVO NODO DESPUÉS DEL NODO ENCONTRADO EN EL PASO ANTERIOR.
     */
    public void insertAtPosition(int position, int data, Pet pet) throws ListDEEExceptionCircular {
        if (nextfirst == null) {
            throw new ListDEEExceptionCircular("La lista está vacía");
        }
        NodeDE nodeDE = new NodeDE(pet);
        nodeDE.setData(nodeDE.getData());
        if (position < 1) {
            throw new ListDEEExceptionCircular("La posición debe ser mayor a 0");
        }
        if (position == 1) {
            nodeDE.setNext(nextfirst);
            nextfirst.setPrevious(nodeDE);
            nodeDE.setPrevious(previouslast);
            previouslast.setNext(nodeDE);
            nextfirst = nodeDE;
        } else {
            NodeDE currentNode = nextfirst;
            int currentPosition = 1;
            while (currentPosition < position - 1 && currentNode.getNext() != nextfirst) {
                currentNode = currentNode.getNext();
                currentPosition++;
            }
            if (currentPosition != position - 1) {
                throw new ListDEEExceptionCircular("La posición es inválida");
            }
            nodeDE.setNext(currentNode.getNext());
            nodeDE.setPrevious(currentNode);
            currentNode.getNext().setPrevious(nodeDE);
            currentNode.setNext(nodeDE);
        }
    }
    /*ALGORITMO  AÑADIR AL INICIO
    CREAR UN NUEVO NODO CON EL VALOR INGRESADO.
    SI LA LISTA ESTÁ VACÍA, ESTABLECER EL NUEVO NODO COMO EL PRIMER Y ÚLTIMO NODO.
    SI LA LISTA NO ESTÁ VACÍA:
    A. INSERTAR EL NUEVO NODO AL INICIO.
    B. ESTABLECER EL NUEVO NODO COMO EL NUEVO PRIMER NODO.
     */
    public void addFirst(int data, Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (nextfirst == null) {
            nextfirst = previouslast = newNode;
        } else {
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
            previouslast.setNext(newNode);
            nextfirst.setPrevious(newNode);
            nextfirst = newNode;
        }
    }
    /*
    ALGORITMO AÑADIR AL FINAL
    CREA UN NUEVO NODO CON LOS DATOS PROPORCIONADOS.
    SI LA LISTA ESTÁ VACÍA, ASIGNA EL NUEVO NODO COMO EL PRIMERO Y EL ÚLTIMO.
    SI LA LISTA NO ESTÁ VACÍA, ASIGNA EL NUEVO NODO COMO EL SIGUIENTE DEL ÚLTIMO NODO
     Y EL ANTERIOR DEL PRIMER NODO.
    ACTUALIZA LAS REFERENCIAS DE LOS NODOS VECINOS PARA INCLUIR EL NUEVO NODO.
    ASIGNA EL NUEVO NODO COMO EL ÚLTIMO NODO DE LA LISTA.
     */
    public void addFinal(int data,Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (nextfirst == null) {
            nextfirst = previouslast = newNode;
        } else {
            newNode.setPrevious(previouslast);
            newNode.setNext(nextfirst);
            previouslast.setNext(newNode);
            nextfirst.setPrevious(newNode);
            previouslast = newNode;
        }
    }
    /*
    CREAR UN NUEVO NODO CON LOS DATOS INGRESADOS.
    VERIFICAR SI LA LISTA ESTÁ VACÍA:
    A. SI ESTÁ VACÍA, ESTABLECER EL NUEVO NODO COMO EL PRIMER Y ÚLTIMO NODO DE LA LISTA.
    HACER QUE EL SIGUIENTE Y ANTERIOR DEL NODO APUNTEN A SÍ MISMO.
    B. SI NO ESTÁ VACÍA, INSERTAR EL NUEVO NODO AL FINAL DE LA LISTA Y ACTUALIZAR LOS ENLACES
     DE LOS NODOS ADYACENTES.
    LANZAR UNA EXCEPCIÓN CON EL MENSAJE "YA ESTÁ BAÑADO EL PERRO DE LA PARTE IZQUIERDA".
     */
    public void addLeftDog(int data, Pet pet)throws ListDEEExceptionCircular {
        NodeDE newNode = new NodeDE(pet);
        if (nextfirst == null) {
            nextfirst = previouslast = newNode;
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
        } else {
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
            previouslast.setNext(newNode);
            nextfirst.setPrevious(newNode);
            previouslast = newNode;
        }
        throw new ListDEEExceptionCircular("ya esta bañado el perro de la parte izquierda  "+data);
    }
    /*
    ALGORIMO BAÑAR AL PERRO LADO DERECHO
    CREAR UN NUEVO NODO CON EL DATO A AGREGAR
    VERIFICAR SI LA LISTA ESTÁ VACÍA:
    A. SI LA LISTA ESTÁ VACÍA, EL NUEVO NODO SERÁ EL PRIMER Y ÚLTIMO NODO
    I. ESTABLECER EL NUEVO NODO COMO EL SIGUIENTE Y ANTERIOR DEL MISMO NODO
    II. ESTABLECER EL PRIMER Y ÚLTIMO NODO COMO EL NUEVO NODO
    B. SI LA LISTA NO ESTÁ VACÍA:
    I. ESTABLECER EL SIGUIENTE DEL NUEVO NODO COMO EL PRIMER NODO ACTUAL
    II. ESTABLECER EL ANTERIOR DEL NUEVO NODO COMO EL ÚLTIMO NODO ACTUAL
    III. ESTABLECER EL SIGUIENTE DEL ÚLTIMO NODO ACTUAL COMO EL NUEVO NODO
    IV. ESTABLECER EL ANTERIOR DEL PRIMER NODO ACTUAL COMO EL NUEVO NODO
    V. ESTABLECER EL PRIMER NODO ACTUAL COMO EL NUEVO NODO
    LANZAR UNA EXCEPCIÓN PERSONALIZADA INDICANDO QUE EL PERRO DE LA PARTE OPUESTA YA HA SIDO BAÑADO.

     */
    public void addRightDog(int data, Pet pet) throws ListDEEExceptionCircular {
        NodeDE newNode = new NodeDE(pet);
        if (nextfirst == null) {
            nextfirst = previouslast = newNode;
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
        } else {
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
            previouslast.setNext(newNode);
            nextfirst.setPrevious(newNode);
            nextfirst = newNode;
        }
        throw new ListDEEExceptionCircular("ya esta bañado el perro de la parte derecha "+data);
    }
    /*
    ALGORITMO PARA BAÑAR TANTO EL LADO DERECHO COMO EL IZQUIERDO
    CREAR UN NUEVO NODO CON EL DATO A AGREGAR.
    SI LA LISTA ESTÁ VACÍA, ASIGNAR EL NUEVO NODO COMO EL PRIMER Y ÚLTIMO NODO, Y ESTABLECER
    LOS ENLACES CIRCULARES.
    SI SE ESPECIFICA QUE SE DEBE AGREGAR AL PRINCIPIO, ESTABLECER LOS ENLACES DEL NUEVO
    NODO CON EL PRIMER Y ÚLTIMO NODO,
    ACTUALIZAR EL PRIMER NODO, Y LANZAR UNA EXCEPCIÓN INDICANDO QUE SE HA BAÑADO AL PERRO.
    DE LO CONTRARIO, ESTABLECER LOS ENLACES DEL NUEVO NODO CON EL PRIMER Y ÚLTIMO NODO, ACTUALIZAR
    EL ÚLTIMO NODO, Y LANZAR UNA EXCEPCIÓN INDICANDO QUE SE HA BAÑADO AL PERRO.

     */
    public void addRightLeftdog(int data, boolean atBeginning, Pet pet) throws ListDEEExceptionCircular {
        NodeDE newNode = new NodeDE(pet);
        if (nextfirst == null) {
            nextfirst = previouslast = newNode;
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
        } else if (atBeginning) {
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
            previouslast.setNext(newNode);
            nextfirst.setPrevious(newNode);
            nextfirst = newNode;
        } else {
            newNode.setNext(nextfirst);
            newNode.setPrevious(previouslast);
            previouslast.setNext(newNode);
            nextfirst.setPrevious(newNode);
            previouslast = newNode;
        }
        throw new ListDEEExceptionCircular("ya se baño al perro  " + data);
    }
}
