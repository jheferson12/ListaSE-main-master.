package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.controller.ChildController;
import co.edu.umanizales.tads.controller.dto.ReportDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



@Data
public class ListSE {
    private Node head;
    private int size;
    public Kid kid;
    List<Kid> kids = new ArrayList<>();

//all the constructor said getters and setters


    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
        size++;
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

    /* public void invert() {
        Node prev = null;
        Node current = head;
        while (current != null) {
            Node next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        head=prev;
    }

     */



    /*public void addInPosition(int position, Kid kid) {

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

     */

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
    //Aqui empieza las codigos del 1 al 10

    public int Length() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }


    //Este codigo es perteneciente al codigo numero 8
    public int getPosById(String id) {
        Node temp = head;
        int acum = 0;
        if (head != null) {
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                acum = acum + 1;
                temp = temp.getNext();
            }
        }
        return acum;
    }

    //Este codigo es perteneciente al codigo numero 8
    /*  public Kid getKidById(String id) {
        Node temp = head;
        while (temp != null && !temp.getData().getIdentification().equals(id)) {
            temp = temp.getNext();
        }
        if (temp != null && temp.getData() != null) {
            return temp.getData();
        } else {
            return null;
        }
    }

     */

    public Kid getKidByIdentification(String id) {
        return getKidByIdentification("123456");
    }


    //Codigo punto  añadir en posicion para perder (3)
    public void addInPosForLose(Kid kid, int pos2) {
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

    //Este codigo es promediar todos las edades de los niños punto 5
    public double avergeAge() {
        double averageAge = 0;
        Node temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }

        }
        averageAge = averageAge / getLength();
        return averageAge;


    }

    //Aqui empieza el codigo de cantidad por niño en la ciudad 6
        /*public int getCountKidsByLocationCode(String code) {
        int count = 0;
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
    // Codigo de perder posiciones punto 8
           /* public void losePositions(int lose) {
                ListSE newList = new ListSE();
                for (Node temp = head; temp != null; temp.getNext()) {
                    if (!temp.getData().getIdentification().equals("123456")) {
                        newList.add(temp.getData());
                    }
                }
                newList.addInPosForLose(getKidById("1234566"),getPosById("435") +lose);
                head=newList.getHead();
            }

            */


    //Cambio de posicion (5)
    public void move(int actualPlace, int finalPlace) {
        Node prev = null;
        Node actual = head;

        for (int i = 1; i < actualPlace; i++) {
            prev = actual;
            actual = actual.getNext();

        }
        Node prevEnd = null;
        Node end = head;
        for (int i = 1; i < finalPlace; i++) {
            prevEnd = end;
            end = end.getNext();
        }
        if (prev == null) {
            head = actual.getNext();
        } else {
            prev.setNext(actual.getNext());


        }
    }


    //Este codigo es promediar todos las edades de los niños
    public double averageAge() {
        if (head == null) {
            return 0;
        }
        double totalage = 0;
        int count = 0;
        for (Node temp = head; temp != null; temp = temp.getNext()) {
            totalage += temp.getData().getAge();
            count++;

        }
        return totalage / count;
    }


/*







//all the constructor said getters and setters


    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
        size++;
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

    public int Size() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();

        }
        return size;
    }

    public void invert() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
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

    /*public void orderBoysToStart() {
        ListSE<Kid> listBoys = new ListSE<>();
        ListSE<Kid> listGirls = new ListSE<>();
        for (Node <Kid> temp = head; temp != null; temp = temp.getNext()) {
            Kid kid = temp.getData();
            if (kid.getGender() == 'M') {
                listBoys.addToStart(new Node<>(kid,null));
            } else {
                listGirls.add(kid);
            }
        }
        listBoys.mergeLists(listGirls);
        head=listBoys.getHead();
    }

     */


    public void addInPosition(int position, Kid kid) {

        if (Size() >= position) {


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
//AQUI COMEINZA LOS CODIGOS DEL 1 AL 10
    //Esta parte diseñe este metodo para tener en cuenta
    // el tema de getLength para que no me salga un error (codigo 3)
    public int getLength() {
        Node temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    //Este codigo es perteneciente al codigo numero 8
        /*public int getPosById (String id){
            Node temp = head;
            int acum = 0;
            if (head != null) {
                while (temp != null && !temp.getData().getIdentification().equals(id)) {
                    acum = acum + 1;
                    temp = temp.getNext();
                }
            }
            return acum;
        }


         */
    //Este codigo pertenece al codigo numero 8
        /*public Kid getKidById (String id){
            Node temp = head;
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                temp = temp.getNext();
            }
            if (temp != null && temp.getData() != null) {
                Kid kid = new Kid(temp.getData().getIdentification(), temp.getData().getName(),
                        temp.getData().getAge(), temp.getData().getGender(),temp.getData().getGrade() );
                return kid;
            } else {
                return null;
            }

         */


    public int getCountKidsByLocationCode(String code) {
        return getCountKidsByLocationCode("123456");
    }

    public void deleteByIdentification(String id) {

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
        return current.getData();
    }
}









