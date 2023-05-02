package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.controller.dto.ReportDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import co.edu.umanizales.tads.exception.ListSEException;


@Data
public class ListSE {
    private Node head;
    private int size;
    public Kid kid;
    List<Kid> kids = new ArrayList<>();

    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getId().equals(kid.getId())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getId().equals(kid.getId())){
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }
    //------------
    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report) {
        if (head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() > age) {
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

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
    public void invert() throws ListSEException{
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }else {
            throw new ListSEException("Head es nulo");
        }
    }

    //-----------------------------CODIGO 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------
    public void getOrderBoysToStart() throws ListSEException {
        if (this.head != null) {
            ListSE listSE = new ListSE();
            Node temp = this.head;
            Node lastBoy = null;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    if (lastBoy != null) {
                        listSE.addToStart(lastBoy.getData());
                    }
                    lastBoy = temp;
                } else {
                    listSE.add(temp.getData());
                }
                temp = temp.getNext();
            }
            if (lastBoy != null) {
                listSE.addToStart(lastBoy.getData());
            }
            this.head = listSE.getHead();
        } else {
            throw new ListSEException("La lista está vacía");
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
    public void getAlternateKids() throws ListSEException {
        Node boys = head;
        Node girls = head.getNext();
        Node girlsHead = girls;
        if (head == null || head.getNext() == null) {
            throw new ListSEException("La lista esta vacia o solo tiene un elemento");
        }
        while (girls != null && boys != null) {
            boys.setNext(girls.getNext());
            if (girls.getNext() != null) {
                girls.setNext(girls.getNext().getNext());
            }
            boys = boys.getNext();
            girls = girls.getNext();
        }
        if (girls == null) {
            boys.setNext(girlsHead);
        } else {
            girls.setNext(girlsHead);
        }
    }


    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA -----------------

    public void removeKidByAge(byte age) throws ListSEException {
        if (age <= 0) {
            throw new ListSEException("La edad debe ser un valor positivo mayor que cero");
        }
        Node current = head;
        Node prev = null;
        while (current != null) {
            if (current.getData().getAge() == age) {
                if (prev == null) {
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
            } else {
                prev = current;
            }
            current = current.getNext();
        }
    }

    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LOS NIÑOS DE LA LISTA -------------------

    public int getLength() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListSEException {
        double averageAge = 0;
        Node temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }
            averageAge = averageAge / getLength();
            return averageAge;

        }else {
            throw new ListSEException("La lista está vacía");
        }
    }

    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD-----------------

    public int getCountKidsByLocationCode(String code) throws ListSEException {
        if (code == null || code.isEmpty()){
            throw new ListSEException("El código de ubicación no puede ser nulo o vacío");
        }
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        } else {
            return 0;
        }
        return count;
    }

    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

    public void winPositionKid(String id, int win) throws ListSEException {
        Node temp = head;
        int sum = 0;
        ListSE listSE = new ListSE();
        if (head != null) {
            while (temp != null && !temp.getData().getId().equals(id)) {
                listSE.add(temp.getData());
                temp = temp.getNext();
            }
            if (temp == null) {
                throw new ListSEException("No se encontró un niño con el ID " + id);
            }
            sum = temp.getData().getPosition() + win;
            if (sum < 0) {
                throw new ListSEException("No se puede mover el niño más allá de la primera posición");
            } else if (sum > size()) {
                throw new ListSEException("No se puede mover el niño más allá de la última posición");
            }
            listSE.add(new Kid(temp.getData().getId(), temp.getData().getName(), sum));
            temp = temp.getNext();
            while (temp != null) {
                listSE.add(temp.getData());
                temp = temp.getNext();
            }
            head = listSE.getHead();
        } else {
            throw new ListSEException("La lista está vacía");
        }
    }


    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void addKidAtPosForLose(Kid kid, int pos2) throws ListSEException {
        Node temp = head;
        Node newNode = new Node(kid);
        int listLength = getLength();
        if (pos2 < 0 || pos2 >= listLength)//to do a validation and add the kid in the last position
            add(kid);
        if (pos2 == 0) {
            newNode.setNext(head);//to actualize the head
            head = newNode;

        } else {
            for (int i = 0; temp.getNext() != null && i < pos2 - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
    }

    //-------------------CODIGO 9 OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES--------------------

    public void reportByAge(byte minAge, byte maxAge) throws ListSEException {
        Node current = head;
        boolean found = false;
        while (current != null) {
            byte edad = current.getData().getAge();
            if (edad >= minAge && edad <= maxAge) {
                String name = current.getData().getName();
                // Aquí puedes hacer lo que quieras con los datos del niño encontrado
                found = true;
            }
            current = current.getNext();
        }
        if (!found) {
            throw new ListSEException("No se encontraron niños dentro del rango de edad especificado.");
        }
    }

    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void addToStartNameChar(char letter) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }
        Node prev = null;
        Node current = head;
        Node last = null;
        while (current != null) {
            if (current.name.startsWith(String.valueOf(letter))) {
                if (prev == null) {
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                if (last == null) {
                    last = current;
                } else {
                    last.setNext(current);
                    last = current;
                }
                current = current.getNext();
                last.setNext(null);
            } else {
                prev = current;
                current = current.getNext();
            }
        }
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
    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }//temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
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


    public void mergeLists(ListSE listGirls) {
        if (this.head == null) {
            this.head = listGirls.getHead();
        } else if (listGirls.getHead() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(listGirls.getHead());
        }
    }
    //-----------------------------Este metodo se usa para otro metodo-----------
    public int getPostById(String id) throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }
        int count = 0;
        Node temp = head;
        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
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
        while (temp != null && !temp.getData().getId().equals(id)) {
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

    public void deleteByIdentifications(String id) {

    }


    public ListDE toDoublyLinkedList() {
        return null;
    }
}









