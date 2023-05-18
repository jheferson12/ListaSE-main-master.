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
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
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
        List<Kid> pets = new ArrayList<>();
        if (head != null) {
            Node current = head;
            while (current != null) {
                pets.add(current.getData());
                current = current.getNext();
            }
        }
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
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();

        }
        return size;
    }

    //---------------------------------CODIGO 1 INVERTIR LA LISTA-------------------------------------------------
    public void invertKid() throws ListSEException {
        if (head == null) {
            throw new ListSEException("No hay elementos en la lista para invertir.");
        }

        ListSE invertedList = new ListSE();
        Node current = head;

        while (current != null) {
            invertedList.addToStart(current.getData());
            current = current.getNext();
        }

        head = invertedList.getHead();
    }


    //-----------------------------CODIGO 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------
    public void ordersKidToStart() throws ListSEException {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        } else {
            throw new ListSEException("No hay niños para completar esta operacion");
        }
    }


    public void addToStart(Kid kid) {
        if (kid == null) {
            return;
        }
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }

    }

    //----------------------CODIGO 3 INTERCALAR NIÑO-NIÑA-NIÑO-NIÑA-------------------------------
    public void alternateKids() throws ListSEException {
        ListSE listCp = new ListSE();
        ListSE boysList = new ListSE();
        ListSE girlsList = new ListSE();

        Node temp = head;

        if (this.head == null && this.head.getNext() == null) {
            throw new ListSEException("No existen niños o no hay suficientes para alternar");
        } else {
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    boysList.add(temp.getData());
                } else {
                    if (temp.getData().getGender() == 'F') {
                        girlsList.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }

            Node nodeBoys = boysList.getHead();
            Node nodeGirls = girlsList.getHead();

            while (nodeBoys != null) {
                if (nodeBoys != null) {
                    listCp.add(nodeBoys.getData());
                    nodeBoys = nodeBoys.getNext();
                }
                if (nodeGirls != null) {
                    listCp.add(nodeGirls.getData());
                    nodeGirls = nodeGirls.getNext();
                }
            }
            this.head = listCp.getHead();
        }
    }


    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA -----------------

    public void deleteByAge(@NotNull Byte age) throws ListSEException {
        if (age == null) {
            throw new ListSEException("la edad no puede ser nula");
        }

        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }

        // Si el nodo a eliminar es el primer nodo
        if (head.getData().getAge() == age) {
            head = head.getNext();
            return;
        }

        Node prevNode = head;
        Node currentNode = head.getNext();

        while (currentNode != null) {
            if (currentNode.getData().getAge() == age) {
                prevNode.setNext(currentNode.getNext());
                return;
            }

            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        throw new ListSEException("No se encontró un nodo con la edad especificada");
    }

    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LOS NIÑOS DE LA LISTA -------------------



    public float getAverageByAge() throws ListSEException {
        if (head != null) {
            Node temp = head;
            int count = 0;
            int age = 0;
            while (temp.getNext() != null) {
                count++;
                age = age + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) age / count;
        } else {
            throw new ListSEException("No hay niños para poder hacer el promedio de edades");
        }
    }


    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD-----------------

    public int getCountKidsByLocationCode(String code) throws ListSEException {
        int count = 0;
        if (this.head == null) {
            throw new ListSEException("No hay niños para realizar esta operacion");
        }
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }




    public int getCountKidsByDeptCode(String code) throws ListSEException {
        try {
            // Llamada al método que realiza el conteo de niños en un departamento específico
            return count(code);
        } catch (ListSEException e) {
            throw new ListSEException("No se puede identificar la cantidad de niños " + e.getMessage());
        }
    }

    private int count(String code) throws ListSEException {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        } else {
            throw new ListSEException("No existen niños para poder realizar la operación");
        }
        return count;
    }


    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

    public void winPositionKid(String id, int position, ListSE listSE) throws ListSEException{
        if (head != null){
            Node temp = this.head;
            int counter = 0;

            while (temp != null && ! temp.getData().getIdentification().equals(id)){
                temp = temp.getNext();
                counter ++;
            }
            int newPosition = counter - position;
            Kid listCopy = temp.getData();
            listSE.deleteById(temp.getData().getIdentification());
            listSE.addKidsByPosition(listCopy , newPosition);
        }
        else {
            throw new ListSEException("La lista esta vacia por lo tanto no se puede completar la accion");
        }
    }

    public void addKidsByPosition(Kid kid, int pos) throws ListSEException {
        Node newNode = new Node(kid);
        try {
            if (pos == 0) {
                newNode.setNext(head);
                head = newNode;
            } else {
                Node current = head;
                for (int i = 1; i < pos - 1; i++) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
        } catch (Exception e) {
            throw new ListSEException("No se puede agregar el niño en la posición indicada: " + e.getMessage());
        }size++;
    }

    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void loseKidPosition(String id, Integer position) throws ListSEException {
        if (position == null || position < 0) {
            throw new ListSEException("La posición debe ser positiva");
        }

        Node current = head;
        Node prevNode = null;
        int count = 0;

        // Buscar la mascota con el ID especificado
        while (current != null && !current.getData().getIdentification().equals(id)) {
            prevNode = current;
            current = current.getNext();
            count++;
        }

        if (current == null) {
            throw new ListSEException("No se encontró una mascota con el ID especificado");
        }

        int newPosition = count + position;
        if (newPosition < 0) {
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
        Node current = head;
        int count = 0;

        while (current != null && count < position) {
            current = current.getNext();
            count++;
        }

        return current;
    }




    //-------------------CODIGO 9 OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES--------------------

    public int getRangeByAge(int first, int last) {
        Node temp = head;
        int count = 0;
        while (temp != null){
            if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
                count ++;
            }
            temp = temp.getNext();
        }
        return count;
    }



    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void getKidByFirstLetter(char letter) throws ListSEException {
        ListSE listCopy = new ListSE();
        Node temp = this.head;

        while (temp != null) {
            if (Character.toUpperCase(temp.getData().getName().charAt(0)) == Character.toUpperCase(letter)) {
                listCopy.addToEnd(temp.getData());
            } else {
                   listCopy.addToStart(temp.getData());
            }
            temp = temp.getNext();
        }

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
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
            size++;
        }

    }








    //-------------------CODIGO BORRAR POR IDENTIFICACCION---------------
    public void deleteById(@NotNull String id) throws ListSEException {
        if (id == null) {
            throw new ListSEException("El identificador del dueño no puede ser nulo");
        }

        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }

        // Si el nodo a eliminar es el primer nodo
        if (head.getData().getIdentification().equals(id)) {
            head = head.getNext();
            return;
        }

        Node prevNode = head;
        Node currentNode = head.getNext();

        while (currentNode != null) {
            if (currentNode.getData().getIdentification().equals(id)) {
                prevNode.setNext(currentNode.getNext());
                return;
            }

            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

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









