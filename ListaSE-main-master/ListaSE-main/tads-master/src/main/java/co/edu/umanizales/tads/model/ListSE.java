package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.controller.dto.ReportDTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import co.edu.umanizales.tads.exception.ListSEException;
import javax.validation.constraints.NotNull;


@Data
public class ListSE {
    private Node head;
    private int size;
    public Kid kid;
    List<Kid> kids = new ArrayList<>();

    public void add(Kid kid) throws ListSEException {
        //PREGUNTO SI LA CABEZA ES DIFERENTE DE NULO
        if (head != null) {
            //SI SE CUMPLE LA CONDICION SE CREA UN NODO TEMPORAL Y QUE SE IGUAL A CABEZA
            Node temp = head;
            //MIENTRAS EL SIGUIENTE DEL NODO TEMPORAL ES DIFERENTE DE NULO
            while (temp.getNext() != null) {
                //ESTE CODIGO REVISA SI EL CODIGO YA FUE REPETIDO O NO SI ESTA REPETIDO LANZA LA EXCEPCION
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException("Ya existe un niño");
                }
                //PERO SI NO ES ASI EL NODO TEMPORAL SERIA EL SIGUIENTE
                temp = temp.getNext();

            }
            //ESTE CODIGO REVISA SI EL CODIGO YA FUE REPETIDO O NO SI ESTA REPETIDO LANZA LA EXCEPCION
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("Ya existe un niño");
            }
            /// SE CREA EL NEWNODE Y SE QUEDA PARADO EN EL ULTIMO
            Node newNode = new Node(kid);
            //AQUI YA CON EL NODO TEMPORAL CON SU SIGUIENTE COJE A NUEVO NODO
            temp.setNext(newNode);
        } else {
            //PERO SI NO SE CUMPLE LO ANTERIOR ENTONCES LA CABEZA SERIA EL NIÑO NUEVO
            head = new Node(kid);
        }
        //AÑADA EL TAMAÑO
        size++;
    }

    //------------


    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */


    /*public void addToStart(Kid kid) {
        Node newNode = new Node(kid);
        if (head != null) {
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

     */


    //---------------METODO DE IMPRIMIR-----------------
    public List<Kid> print() {
        //SE CREA CON LA CLASE KID UN NOMBRE PETS PARA QUE ME GUARDE LOS DATOS
        List<Kid> pets = new ArrayList<>();
        //PREGUNTAMOS SI CABEZA ES NULA
        if (head != null) {
            //NODO ACTUAL ES IGUAL A LA CABEZA
            Node current = head;
            //PERO MIENTRAS EL NODO ACTUAL SEA DIFERENTE DE NULO
            while (current != null) {
                //DE EL NOMBRE DE LA LISTA QUE LE AÑADA LOS DATOS DEL NODO ACTUAL
                pets.add(current.getData());
                //Y CON EL NODO ACTUAL SA IGUAL AL SIGUIENTE
                current = current.getNext();
            }
        }
        //QUE ME RETORNE LA LISTA
        return pets;
    }





    /*algoritmo para adicionar en posicion
    primero saber el tamaño de la lista con un metodo que recorra  la lista hasta que no haya un niño siguiente y en cada vuelta del bucle se va contando
    luego se crea un meotdo donde recive el niño nuevo y en donde se quiere ser agregado
    si la posicion que se quiere es coherente con el tamaño de la lista
      entonces
        se crea el niño
         si la posicion donde se quiere adicionar es 0  lo que sera la cabeza entonces
            entonces  se llamara el metodo que agrega el niño al inicio
         en caso de que sea otra posicion diferente a 0
            entonces se crea un mensajero para que vaya una posicion antes de la pedida
                cuando se llegue a a la posicion entonces
                    se crea un nuevo nodo
                    y el siguiente niño del nuevo nodo sera lo siguiente que teniea el menesajero
                    y lo siguiente del mensjaero sera el nuevo nodo

        en caso de que la posicion no sea posible se agrega al final con un metood agregar

    * */

    public int size() {
        //CREO UNA VARIABLE INICIALIZADA EN 0
        int size = 0;
        //NODO TEMPORAR SERIA IGUAL A LA CABEZA
        Node temp = head;
        //MIENTRAS EL NODO TEMPORAL SEA DIFERENTE DE NULO
        while (temp != null) {
            //EL TAMAÑO SE ME VALLA SUMANDO EN 1
            size++;
            //EL NODO TEMPORAL SERIA EL SIGUIENTE DEL NODO
            temp = temp.getNext();

        }
        //RETORNE EL TAMAÑO
        return size;
    }

    //---------------------------------CODIGO 1 INVERTIR LA LISTA-------------------------------------------------
    public void invertKid() throws ListSEException {
        //PREGUNTO SI CABEZA ES IGUAL A NULA SI ES ASI SE DA UNA EXCEPCION
        if (head == null) {
            throw new ListSEException("No hay elementos en la lista para invertir.");
        }
            //SE CREA UNA LISTA LLAMADA IVERTEDLIST
        ListSE invertedList = new ListSE();
            //NODO ACTUAL SERIA IGUAL A LA CABEZA
        Node current = head;
            //MIENTRAS EN NODO ACTUAL SEA DIFERENTE DE NULO
        while (current != null) {
            //SE LLAMA LA LISTA Y QUE ME AÑADA AL PRINCIPIO LOS DATOS DEL NODO ACTUAL
            invertedList.addToStart(current.getData());
            //NODO ACTUAL SERIA EL SIGUIENTE DEL NODO
            current = current.getNext();
        }
            //CABEZA SERIA IGUAL A LA CABEZA DE LA LISTA
        head = invertedList.getHead();
    }


    //-----------------------------CODIGO 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------
    public void ordersKidToStart() throws ListSEException {
        //INSTANCIA ACTUAL DE LA CABEZA SI ES DIFERENTE DE NULO
        if (this.head != null) {
            //AÑADIENDO EL NOMBRE A LA LISTA LISTCP
            ListSE listCp = new ListSE();
            //EL NODO TEMPORAL SE ASIGNA EL VALOR A LA ISNTANCIA
            Node temp = this.head;
            //MIENTRAS EL NODO SEA DIFERENTE DE NULO
            while (temp != null) {
                //SI LOS DATOS DEL GENERO QUE TIENE EL NODO QUE SEA IGUAL A LA LETRA DADA
                if (temp.getData().getGender() == 'M') {
                    //LISTA SE AÑADE AL COMIENZO LOS DATOS DEL NODO TEMPORAL
                    listCp.addToStart(temp.getData());
                } else {
                    //LISTA AÑADE LOS DATOS DEL NODO TEMPORAL
                    listCp.add(temp.getData());
                }
                //NODO TEMPORAL TOMA EL VALOR DE SU SIGUIENTE
                temp = temp.getNext();
            }
            //LA INSTANCIA DE LA CABEZA SERIA IGUAL A LA CABEZA DE LA LISTA
            this.head = listCp.getHead();
        } else {
            //SI NO SE CUMPLE LO ANTERIOR SE ARROJA UNA EXCEPCION
            throw new ListSEException("No hay niños para completar esta operacion");
        }
    }


    public void addToStart(Kid kid) {
        //SI KID ES IGUAL A NULO (TIPO RECTIFICACION)
        if (kid == null) {
            //SE RETORNA SIN NINGUNA VARIABLE
            return;
        }
        if (head != null) {
            //SE CREA UN NODO LLAMADO NUEVO NODO
            Node newNode = new Node(kid);
            //CON SI SIGUIENTE EL NUEVO NODO COJE A LA CABEZA
            newNode.setNext(head);
            //LA CABEZA SE AÑADE A EL VALOR DEL NUEVO NODO
            head = newNode;
        } else {
            //SI NO LA CABEZA SERIA IGUAL A EL NIÑO AGREGANDO A LA CLASE NODO
            head = new Node(kid);
        }

    }

    //----------------------CODIGO 3 INTERCALAR NIÑO-NIÑA-NIÑO-NIÑA-------------------------------
    public void alternateKids() throws ListSEException {
        //SE AÑADE LA LISTA SE COMO NOMBRE LISTCP
        ListSE listCp = new ListSE();
        //LISTA PARA NIÑOS
        ListSE boysList = new ListSE();
        //LISTA PARA LAS NIÑAS
        ListSE girlsList = new ListSE();
        //NODO TEMPORAL SERIA IGUAL A LA CABEZA
        Node temp = head;
        //INSTACIA ES IGUAL A NULO Y INSTANCIA IGUAL A NULO EXCEPCION
        if (this.head == null && this.head.getNext() == null) {
            throw new ListSEException("No existen niños o no hay suficientes para alternar");
        } else {
            //NODO TEMPORAL ES DIFERENTE DE NULO
            while (temp != null) {
                //LOS DATOS DE EL GENERO DEL NODO TEMPORAL SERIA IGUAL A LA LETRA DADA
                if (temp.getData().getGender() == 'M') {
                    //LISTA DE NIÑOS QUE ME AÑADA LOS DATOS DE EL NODO TEMPORAL
                    boysList.add(temp.getData());
                } else {
                    //LOS DATOS DE EL GENERO DEL NODO TEMPORAL SERIA IGUAL A LA LETRA DADA
                    if (temp.getData().getGender() == 'F') {
                        //LISTA DE NIÑAS QUE ME AÑADA LOS DATOS DE EL NODO TEMPORAL
                        girlsList.add(temp.getData());
                    }
                }//NODO TEMPORAL QUIVALENTE CON EL SIGUIENTE DEL NODO TEMPORAL
                temp = temp.getNext();
            }
                //CREA NODO PARA LOS NIÑOS
            Node nodeBoys = boysList.getHead();
            //CREA NODO PARA LAS NIÑAS
            Node nodeGirls = girlsList.getHead();
            //NODO DE NIÑOS ES DIFERENTE DE NULO
            while (nodeBoys != null) {
                //SI PASA LO MISMO ATERIOR
                if (nodeBoys != null) {
                    //LISTA QUE SE AÑADA LOS DATOS DEL NODO DE LOS NIÑOS
                    listCp.add(nodeBoys.getData());
                    //NODO DE LOS NIÑOS SEA IGUAL AL SIGUIENTE DEL NODO DE LOS NIÑOS
                    nodeBoys = nodeBoys.getNext();
                }
                //NODO NIÑAS DIFERENTE DE NULO
                if (nodeGirls != null) {
                    //LISTA QUE SE AÑADA LOS DATOS DEL NODO DE LAS NIÑAS
                    listCp.add(nodeGirls.getData());
                    //NODO DE LAS NIÑAS SERIA IGUAL AL SIGUIENTE DEL NODO DE LAS NIÑAS
                    nodeGirls = nodeGirls.getNext();
                }
            }
            //INSTANCIA ES IGUAL A LA CABEZA DE LA LISTA
            this.head = listCp.getHead();
        }
    }


    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA -----------------

    public void deleteByAge(@NotNull Byte age) throws ListSEException {
        //PREGUNTA SI LA EDAD ES IGUAL A NULO SI CUMPLE EXCEPCION
        if (age == null) {
            throw new ListSEException("la edad no puede ser nula");
        }
        //PREGUNTAR SI LA CABEZA ES IGAUL A NULO SI CUMPLE CONDICION

        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }

        // Si el nodo a eliminar es el primer nodo
        if (head.getData().getAge() == age) {
            head = head.getNext();
            return;
        }
        //NODO ANTERIOR SERIA IGUAL A LA CABEZA
        Node prevNode = head;
        //NODO ACTUAL ES IGUAL A SIGUIENTE CABEZA
        Node currentNode = head.getNext();
        //NODO ACTUAL DIFERENTE DE NULO
        while (currentNode != null) {
            //LOS DATOS DEL NODO ACTUAL SEA LA EDAD
            if (currentNode.getData().getAge() == age) {
                //NODO ANTERIOR CAMBIARIA EL NODO SIGUIENTE CON EL SIGUINETE DEL NODO ACTUAL
                prevNode.setNext(currentNode.getNext());
                return;
            }
            //NODO ANTERIOR SEA IGUAL QUE EK NODO ACTUAL
            prevNode = currentNode;
            //NODO ACTUAL SEA IGUAL AL SIGUIENTE DEL SIGUIENTE NODO
            currentNode = currentNode.getNext();
        }
        //SI NO EXCEPCION

        throw new ListSEException("No se encontró un nodo con la edad especificada");
    }

    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LOS NIÑOS DE LA LISTA -------------------



    public float getAverageByAge() throws ListSEException {
        //CABEZA ES DIFERENTE DE NULO
        if (head != null) {
            //NODO TEMPORAL ES IGUAL A LA CABEZA
            Node temp = head;
            //VARIABLES INICIALIZADAS EN 0
            int count = 0;
            int age = 0;
            //EL SIGUIENTE DEL NODO DTEMPORAL ES DIFERENTE DE NULO
            while (temp.getNext() != null) {
                //CONTADO SUMA EN 1
                count++;
                //PARA SABER LA EDAD SE SUMA LA EDAD Y SERIA TENER LOS DATOS CON LA EDAD SUMARLAS
                age = age + temp.getData().getAge();
                //NODO TEMPORAL ES IGUAL A EL SIGUIENTE DEL NODO TEMPORAL
                temp = temp.getNext();
            }
            //RETORNE DESARROLLO
            return (float) age / count;
        } else {
            //SI NO EXCEPCION
            throw new ListSEException("No hay niños para poder hacer el promedio de edades");
        }
    }


    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD-----------------

    public int getCountKidsByLocationCode(String code) throws ListSEException {
        //VARIABLE INCIALIZADA EN 0
        int count = 0;
        //INSTANCIA ES DIFERENTE DE NULO
        if (this.head == null) {
            //EXCEPCION
            throw new ListSEException("No hay niños para realizar esta operacion");
        }
        //INSTANCIA ES DIFENETE DE NULO
        if (this.head != null) {
            //NODO TEMPORAL SEIA IGUAL A LA INSTANCIA
            Node temp = this.head;
            //NODO TEMPORAL ES DIFERENTE DE NULO
            while (temp != null) {
                //SE COMPARA COMO TAL LOS CODIGOS QUE ESTAN EN LA UBICACION
                if (temp.getData().getLocation().getCode().equals(code)) {
                    //CONTADOR EN 1
                    count++;
                }
                //NODO TEMPORAL ES IGUAL AL SIGUIENTE
                temp = temp.getNext();
            }
        }
        //RETORNE EL CONTADOR
        return count;
    }

    public int getCountKidsByDeptCode(String code) throws ListSEException {
        try {
            // Llamada al método que realiza el conteo de niños en un departamento específico
            return count(code);
        } catch (ListSEException e) {
            throw new ListSEException("No se puede identificar la cantidad de niños " + e.getMessage());
        }
    }//preguntr si estan bañados

    private int count(String code) throws ListSEException {
        //VARIABLE CONT INICIA EN 0
        int count = 0;
        //INSTACIA ES DIFERENTE DE NULO
        if (this.head != null) {
            //NODO TEMPORAL SERIA LA INSTANCIA
            Node temp = this.head;
            //CABEZA ES DIFERENTE DE NULO
            while (temp != null) {
                //EXTRAEL CADENA DE TEXTO CON INTERVALOS DE 0 A 5 Y COMPARANDO EL CODIGO DE LA LOCALIZACION
                if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {
                    //CONTADOR +1
                    count++;
                }
                //NODO TEMPORAL SERIA EL SIGUIENTE
                temp = temp.getNext();
            }
        } else {
            //EXCEPCION
            throw new ListSEException("No existen niños para poder realizar la operación");
        }
        //SE RETORNA COUNT
        return count;
    }


    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

    public void winPositionKid(String id, int position, ListSE listSE) throws ListSEException{
        //CABEZA ES DIFERENTE DE NULO
        if (head != null){
            //NODO TEMPORARL SEIA IGUAL A LA INSTACIA
            Node temp = this.head;
            //EL CONTADOR EN 0
            int counter = 0;
            //EN NODO TEMPORAL DIFERENTE DE NULO ESN DIFERENTE A LA COMPARACION DE LA IDENTIFICACION
            while (temp != null && ! temp.getData().getIdentification().equals(id)){
                //NODO TEMPORRAL SERIA AL SIGUIENTE
                temp = temp.getNext();
                //CONTADOR +1
                counter ++;
            }
            //REUBICACION DEL ELEMENTO DE LA LISTA
            int newPosition = counter - position;
            Kid listCopy = temp.getData();
            listSE.deleteById(temp.getData().getIdentification());
            listSE.addKidsByPosition(listCopy , newPosition);
        }
        else {
            //EXCEPCION
            throw new ListSEException("La lista esta vacia por lo tanto no se puede completar la accion");
        }
    }

    public void addKidsByPosition(Kid kid, int pos) throws ListSEException {
        //SE CREA UN NUEVO NODO
        Node newNode = new Node(kid);
        try {
            //VARIABLE POS ES IGUAL A 0
            if (pos == 0) {
                //NUEVO NODO DE SE CAMBIA A EL SIGUIENTE DE LA CABEZA
                newNode.setNext(head);
                //CABEZA ES NUEVO NODO
                head = newNode;
            } else {
                //NODO ACTUAL ES IGUAL A CABEZA
                Node current = head;
                //SE AÑADE UN LOOP FOR
                for (int i = 1; i < pos - 1; i++) {
                    current = current.getNext();
                }
                //NUEVO NODO SE CAMIBARIA POR EL SIGUIENTE DE EL NODO ACTUAL
                newNode.setNext(current.getNext());
                //NODO ACTUAL SE CABIA A NUEVO NODO
                current.setNext(newNode);
            }
            //EXCEPCION
        } catch (Exception e) {
            throw new ListSEException("No se puede agregar el niño en la posición indicada: " + e.getMessage());
        }size++;
    }

    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void loseKidPosition(String id, Integer position) throws ListSEException {
        //TIENEN QUE SER LOS DOS VERDADEROS
        if (position == null || position < 0) {
            //SI SE CUMPLE EXCEPCION
            throw new ListSEException("La posición debe ser positiva");
        }
        //NODO ACTUAL ES IGUAL A LA CABEZA
        Node current = head;
        //NODO PREVIO ES IGUAL A NULO
        Node prevNode = null;
        //VARIABLE CONT IGUALADA EN 0
        int count = 0;

        // Buscar la mascota con el ID especificado
        while (current != null && !current.getData().getIdentification().equals(id)) {
            //NODO PREVIO SERIA IGUAL AL NODO ACTUAL
            prevNode = current;
            //NODO ACTUAL SERIA EL SIGUIENTE DEL NODO
            current = current.getNext();
            //CONT +1
            count++;
        }
        //NOODO ACTUAL ES IGUAL A NULO
        if (current == null) {
            //EXCEPCION
            throw new ListSEException("No se encontró una mascota con el ID especificado");
        }
        //LA NUEVA POSICION SE SUMA LAS VARIABLES
        int newPosition = count + position;
        //NUEVA POSICION ES MENOR QUE 0
        if (newPosition < 0) {
            //EXCEPCION
            throw new ListSEException("La posición resultante es inválida");
        }

        // Eliminar la mascota de la posición actual
        if (prevNode == null) {
            head = current.getNext();
        } else {
            prevNode.setNext(current.getNext());
        }

        // Insertar la mascota en la nueva posición
        current.setNext(getNodeAtPosition(newPosition));
        if (newPosition == 0) {
            head = current;
        }
    }

    private Node getNodeAtPosition(int position) {
        //NODO ACTUAL IGUAL A LA CABEZA
        Node current = head;
        //VARIABLE INICIAL EN 0
        int count = 0;
        //MIENTRAS CURENTE SEA DIFERENTE DE NULO O CONTADOR ES MENOR QUE POSICION
        while (current != null && count < position) {
            //NODO ACTUAL ES EL NODO SIGUIENTE
            current = current.getNext();
            //CONTADOR +1
            count++;
        }
        //RETORNAR VALOR DEL CONTADOR
        return current;
    }




    //-------------------CODIGO 9 OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES--------------------

    public int getRangeByAge(int first, int last) {
        //NODO TEMPORAL IGUAL A CABEZA
        Node temp = head;
        //VARIABLE INICIADA EN 0
        int count = 0;
        //TEMPORAL DIFERENTE DE NULO
        while (temp != null){
            if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
                //CONTADOR +1
                count ++;
            }
            //NODO TEMPORAL ES IGUAL A EL EL SIGUIENTE DEL NODO TEMPORAL
            temp = temp.getNext();
        }
        //RETORNAR CONTADOR
        return count;
    }



    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void getKidByFirstLetter(char letter) throws ListSEException {
        //LISTA CON NOMBRE LISTA COPIA
        ListSE listCopy = new ListSE();
        //NODO TEMPORAL UGUAL A LA INSTACIA
        Node temp = this.head;
        //NODO TEMPORAL DIFERENTE DE NULO
        while (temp != null) {
            //SE HACE UN TIPO CONVERTIDOR PARA QUE TODO LO QUE SE INGRESE QUEDE EN MAYUSCULA
            if (Character.toUpperCase(temp.getData().getName().charAt(0)) == Character.toUpperCase(letter)) {
                //LISTA COPIA AÑADOA LOS DATOS DEL NODO FINAL
                listCopy.addToEnd(temp.getData());
            } else {
                //LISTA COPIA AÑADA AL COMIENZO LOS DATOS DEL NODO TEMPORAL
                   listCopy.addToStart(temp.getData());
            }
            //NODO TEMPORAL SERIA EL SIGUIENTE DEL NODO
            temp = temp.getNext();
        }
        //INSTACIA ES LA CABEZA DE LA LISTA COPIA

        this.head = listCopy.getHead();

        /*
        if (listCopy.getHead() != null) {
            Node lastNode = this.head;
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }

            Node copyNode = listCopy.getHead();
            while (copyNode != null) {
                lastNode.setNext(new Node(copyNode.getData()));
                lastNode = lastNode.getNext();
                copyNode = copyNode.getNext();
            }
        } else {
            throw new ListSEException("No se encontraron niños con la letra especificada.");
        }

         */
    }


    public void addToEnd(Kid data) {
        //NUEVO NODO
        Node newNode = new Node(data);
        //CABEZA ES DIFERENTE DE NULO
        if (head == null) {
            //CABEZA SE AÑADE AL NUEVO NODO
            head = newNode;
        } else {
            //NODO ACTAL SERIA LA CABEZA
            Node current = head;
            //EL SIGUIENTE DEL NODO ES DIFERENTE DE NULO
            while (current.getNext() != null) {
                //EL NODO ACTUAL QUE SEA EL SIGUINETE DEL NODO
                current = current.getNext();
            }
            //EL NODO ACTUAL SE CAMBIA COMO NUEVO NODO
            current.setNext(newNode);
            size++;
        }

    }

    //-------------------CODIGO BORRAR POR IDENTIFICACCION---------------
    public void deleteById(@NotNull String id) throws ListSEException {
        //EL ID ES DIFERENTE DE NULO
        if (id == null) {
            //EXCEPCION
            throw new ListSEException("El identificador del dueño no puede ser nulo");
        }
        //CABEZA DIFERENTE DE NULO
        if (head == null) {
            //EXCEPCION
            throw new ListSEException("La lista está vacía");
        }

        // Si el nodo a eliminar es el primer nodo
        if (head.getData().getIdentification().equals(id)) {
            head = head.getNext();
            return;
        }
        //NODO PREVIO SEIA IGUAL A LA CABEZA
        Node prevNode = head;
        //NODO PREVIO IGUAL A EL SIGUINETE DE LA CABEZA
        Node currentNode = head.getNext();
        //NODO ACTUAL ES DIFERENTE DE NULO
        while (currentNode != null) {
            //EL NODO ACTUAL HACE UNA COPARACION CON LOS DATOS DE LA IDENTIFICACION
            if (currentNode.getData().getIdentification().equals(id)) {
                //EL NODO PREVIO SE CABIARIA EL SIGUINETE AL SIGUIETE DEL NODO ACTUAL
                prevNode.setNext(currentNode.getNext());
                return;
            }
            //NODO PREVIO SERIA IGUAL A EL NODO ACTUAL
            prevNode = currentNode;
            //NODO ACTUAL SERIA EL SIGUIENTE DEL NODO
            currentNode = currentNode.getNext();
        }
        //EXCEPCION

        throw new ListSEException("No se encontró un nodo con el ID especificado");
    }

    /*
    Estos codigo que se ven sobrantes los utilizare depronto por algun codigo que tengan complicaciones

 */
    public void addInPosition(int position, Kid kid) throws ListSEException {
        if (size() >= position) {
            if (position == 0) {
                addToStart(kid);
            } else {
                Node temp = head;
                for (int i = 0; i < position - 1; i++) {
                    temp = temp.getNext();
                }
                Node newNode = new Node(kid);
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
        } else {
            add(kid);
        }
    }



    /*
     * algoritmo para eliminar por id
     * se crea una mensajero que sea igual a la cabeza
     * y otro nodo el cual sera para indentificar al anterior nodo
     * lugo se recorre la lista solo en caso de que el mensajer tenga algun valor y en caso de no se haya encontrado el niño con ese id
     * a dentro del bucle el segudno nodo sera igual al mensajero y el mensajero para a otra niño asi hasta que termine el ciclo
     * en caso de que el mensajero no haya quedado vacio
     * entonces
     *       si el nodo previous  es null es porque al que se quiere eliminar es a la cabeza  entonces nunca entraron al bulce
     *           entonces la cabeza sera el valor que le seguia al mensajero o sea el valor sigueite a la cabeza
     *
     *
     *    */

/*Estos codigos es por si esta malo los otros que estan organizados
 en el comienzo se remplaza el codigo de arriba con estos
    teniendo en cuenta los puntos pertenecientes

 */





    //Codigo cambio de extremos (1)
    public void changeExtremes()throws ListSEException {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }//temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }else{
            throw new ListSEException("hay un problema en cambiar los extremos");
        }


    }
//all the constructor said getters and setters

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */





    /*algoritmo para adicionar en posicion
    primero saber el tamaño de la lista con un metodo que recorra  la lista hasta que no haya un niño siguiente y en cada vuelta del bucle se va contando
    luego se crea un meotdo donde recive el niño nuevo y en donde se quiere ser agregado
    si la posicion que se quiere es coherente con el tamaño de la lista
      entonces
        se crea el niño
         si la posicion donde se quiere adicionar es 0  lo que sera la cabeza entonces
            entonces  se llamara el metodo que agrega el niño al inicio
         en caso de que sea otra posicion diferente a 0
            entonces se crea un mensajero para que vaya una posicion antes de la pedida
                cuando se llegue a a la posicion entonces
                    se crea un nuevo nodo
                    y el siguiente niño del nuevo nodo sera lo siguiente que teniea el menesajero
                    y lo siguiente del mensjaero sera el nuevo nodo

        en caso de que la posicion no sea posible se agrega al final con un metood agregar

    * */

    public int getSize() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();

        }
        return size;
    }



    //-----------------------------Este metodo se usa para otro metodo-----------
    public int getPostById(String id) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }
        int count = 0;
        Node temp = head;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(id)) {
                return count;
            }
            temp = temp.getNext();
            count++;
        }
        return -1;
    }





    /*
     * algoritmo para eliminar por id
     * se crea una mensajero que sea igual a la cabeza
     * y otro nodo el cual sera para indentificar al anterior nodo
     * lugo se recorre la lista solo en caso de que el mensajer tenga algun valor y en caso de no se haya encontrado el niño con ese id
     * a dentro del bucle el segudno nodo sera igual al mensajero y el mensajero para a otra niño asi hasta que termine el ciclo
     * en caso de que el mensajero no haya quedado vacio
     * entonces
     *       si el nodo previous  es null es porque al que se quiere eliminar es a la cabeza  entonces nunca entraron al bulce
     *           entonces la cabeza sera el valor que le seguia al mensajero o sea el valor sigueite a la cabeza
     *
     *
     *    */

    //Estos codigos lo tengo en cuenta depronto por algunos problemas que tengan los codigos en la parte principal


    //Este codigo pertenece al codigo numero 8
    public Kid getKidById(String id) throws ListSEException {
        Node temp = head;
        while (temp != null && !temp.getData().getIdentification().equals(id)) {
            temp = temp.getNext();
        }
        if (temp != null && temp.getData() != null) {
            return temp.getData();
        } else {
            throw new ListSEException("No se encontró el niño con ID " + id);
        }
    }




    //----------------------------------Codigo de el dia 24/04/23------------------------------------

    //En este caso se puede comenzar diciendo que
    // he creado una clase que se llame ChildControler ya pues tenemos tener en cuentas unos codigos

    public ReportDTO getReport(byte age) {
        ListSE listSE = new ListSE();
        Node current = listSE.getHead();
        while (current != null && current.getNext() != null) {
            current = current.getNext();
        }
       if (current==null){
           return null;
       }else {
           current.getData().setAge(age);

       }
        return getReport((byte) 12);
    }











}









