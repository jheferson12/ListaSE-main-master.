package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.controller.dto.ReportDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import co.edu.umanizales.tads.exception.ListSEException;
import org.springframework.web.bind.annotation.PathVariable;


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
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
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
    public void deleteByidentification (String identification){
        Node currentNode = head;
        Node prevNode = null;

        while (currentNode != null && currentNode.getData().getIdentification() != identification) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if(currentNode != null){
            if (prevNode == null){
                head = currentNode.getNext();
            }else {
                prevNode.setNext(currentNode.getNext());
            }
        }
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
    public void getinvert() throws ListSEException {
        try {
            if (this.head == null) {
                throw new ListSEException("No hay niños para poder invertir la lista");
            } else {
                ListSE listCp = new ListSE();
                Node temp = this.head;
                while (temp != null) {
                    listCp.addToStart(temp.getData());
                    temp = temp.getNext();
                }
                this.head = listCp.getHead();
            }
        } catch (ListSEException e) {
            // manejo de la excepción aquí
            // por ejemplo, puedes imprimir el mensaje de error
            throw new ListSEException("Se ha producido un error al invertir la lista: " + e.getMessage());
        }
    }


    //-----------------------------CODIGO 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------
    public void getOrderBoysToStart() throws ListSEException {
        try {
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
        } catch (ListSEException e) {
            // manejo de la excepción aquí
            // por ejemplo, puedes imprimir el mensaje de error
            throw new ListSEException("Se ha producido un error al ordenar la lista de niños: " + e.getMessage());
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
        try {
            ListSE alternateList = new ListSE();

            ListSE listBoys = new ListSE();
            ListSE listGirls = new ListSE();

            Node temp = head;

            if (this.head == null && this.head.getNext() == null) {
                throw new ListSEException("No existen niños o no hay suficientes para alternar");
            } else {
                while (temp != null) {
                    if (temp.getData().getGender() == 'M') {
                        listBoys.add(temp.getData());
                    } else {
                        if (temp.getData().getGender() == 'F') {
                            listGirls.add(temp.getData());
                        }
                    }
                    temp = temp.getNext();
                }

                Node boysNode = listBoys.getHead();
                Node girlsNode = listGirls.getHead();

                while (boysNode != null) {
                    if (boysNode != null) {
                        alternateList.add(boysNode.getData());
                        boysNode = boysNode.getNext();
                    }
                    if (girlsNode != null) {
                        alternateList.add(girlsNode.getData());
                        girlsNode = girlsNode.getNext();
                    }
                }
                this.head = alternateList.getHead();
            }
        } catch (NullPointerException e) {
            throw new ListSEException("Error de puntero nulo al tratar de alternar niños");
        }
    }


    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA -----------------

    public void removeKidByAge(Byte age) throws ListSEException {
        Node temp = head;
        ListSE listcopy = new ListSE();
        try {
            if (age <= 0) {
                throw new ListSEException("La edad debe ser mayor que cero");
            } else {
                if (this.head == null) {
                    throw new ListSEException("No existen niños para realizar la operación");
                } else {

                    while (temp != null) {
                        if (temp.getData().getAge() != age) {
                            listcopy.addToStart(temp.getData());
                        }
                        temp = temp.getNext();
                    }
                    this.head = listcopy.getHead();

                }
            }
        } catch (ListSEException e) {
            throw new ListSEException("Error al remover niños por edad: " + e.getMessage());
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

    public float getAverageAge() throws ListSEException {
        try {
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
        } catch (ArithmeticException e) {
            throw new ListSEException("No se pudo calcular el promedio de edades debido a una excepción aritmética: " + e.getMessage());
        }
    }


    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD-----------------

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report) throws ListSEException {
        try {
            if (head != null) {
                Node temp = this.head;
                while (temp != null) {
                    if (temp.getData().getAge() > age) {
                        report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                    }
                    temp = temp.getNext();
                }
            } else {
                throw new ListSEException("No existen niños para poder realizar la función");
            }
        } catch (ListSEException e) {
            throw new ListSEException("Error al generar el reporte de niños por ubicación y género: " + e.getMessage());
        }
    }

    public int getCountKidsByLocationCode(String code) throws ListSEException{
        int count =0;
        if (this.head == null){
            throw new ListSEException("No hay niños para realizar esta operacion");
        }
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
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

    public void winPositionKid(String id, int position, ListSE listSE) throws ListSEException {
        try {
            if (!this.head.getData().getIdentification().equals(id)) {
                throw new ListSEException("No existe el niño que busca");
            }

            if (head != null) {
                Node temp = this.head;
                int count = 0;

                while (temp != null && !temp.getData().getIdentification().equals(id)) {
                    temp = temp.getNext();
                    count++;
                }

                if (count > size || count < size) {
                    throw new ListSEException("No se puede realizar la accion por falta de niños");
                }

                int newPosition = count - position;
                Kid listCopy = temp.getData();
                listSE.deleteKidByIdentification(temp.getData().getIdentification());
                listSE.addKidsByPosition(listCopy, newPosition);

            } else {
                throw new ListSEException("No existen niños para poder realizar la función");
            }
        } catch (ListSEException e) {
            throw new ListSEException("Error en el método winPositionKid(): " + e.getMessage());
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
        }
    }

    public void deleteKidByIdentification(String identification) throws ListSEException {
        try {
            Node temp = head;
            Node Nodeanterior = null;
            while ((temp != null) && (!temp.getData().getIdentification().equals(identification))) {
                Nodeanterior = temp;
                temp = temp.getNext();
            }
            if (temp != null) {
                if (Nodeanterior == null) {
                    head = temp.getNext();
                } else {
                    Nodeanterior.setNext(temp.getNext());
                }
            } else {
                throw new ListSEException("No se encontró al niño con la identificación " + identification);
            }
        } catch (ListSEException e) {
            throw new ListSEException("No se pudo eliminar al niño con la identificación " + identification + ": " + e.getMessage());
        }
    }




    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void lostPositionKid(String id, int position, ListSE listSE) throws ListSEException {
        try {
            Node temp = this.head;
            int count = 1;

            if (head != null) {
                while (temp != null && !temp.getData().getIdentification().equals(id)) {
                    temp = temp.getNext();
                    count++;
                }
                int newPosition = position + count;
                Kid listCopy = temp.getData();
                listSE.deleteKidByIdentification(temp.getData().getIdentification());
                listSE.addKidsByPosition(listCopy, newPosition);

            } else {
                throw new ListSEException("No existen niños para poder realizar la función");
            }
        } catch (ListSEException e) {
            throw new ListSEException("No se puede ejecutar la función debido a un error: " + e.getMessage());
        }
    }



    //-------------------CODIGO 9 OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES--------------------

    public int getAgeByRange(int letter, int last) throws ListSEException {
        Node temp = head;
        int count = 1;

        try {
            if (this.head == null) {
                throw new ListSEException("No existen niños para poder realizar la operación");
            } else {
                while (temp != null) {
                    if (temp.getData().getAge() >= letter && temp.getData().getAge() <= last) {
                        count++;
                    }
                    temp = temp.getNext();
                }
                return count;
            }
        } catch (ListSEException e) {
            throw new ListSEException("No se puede generar el reporte: " + e.getMessage());
        }
    }



    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void addToStartNameChar(char letter) throws ListSEException {
        ListSE listCopy = new ListSE();
        Node temp = this.head;

        try {
            if (this.head == null) {
                throw new ListSEException("No existen niños para poder realizar la operación");
            } else {
                while (temp != null) {
                    if (temp.getData().getName().charAt(0) != Character.toUpperCase(letter)) {
                        listCopy.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }

            temp = this.head;

            if (this.head == null) {
                throw new ListSEException("No existen niños para poder realizar la operación");
            } else {
                while (temp != null) {
                    if (temp.getData().getName().charAt(0) == Character.toUpperCase(letter)) {
                        listCopy.add(temp.getData());
                    }
                    temp = temp.getNext();
                }
            }
            this.head = listCopy.getHead();
        } catch (ListSEException e) {
            throw new ListSEException("Error intenta de nuevo  " + e.getMessage());
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





    public void deleteByIdentifications(String id) {

    }



    public ListDE toDoublyLinkedList() {
        return null;
    }

}









