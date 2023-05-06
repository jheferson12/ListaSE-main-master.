package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ReportPetsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListDE {
    private NodeDE headDE;
    private int size;


    public NodeDE getHead() {
        return headDE;
    }

    public void add(@NotNull @Valid Pet pet) throws ListDEException {
        try {
            if (headDE != null) {
                NodeDE temp = headDE;
                while (temp.getNext() != null) {
                    temp.getNext();
                }
                NodeDE newNodeDE = new NodeDE(pet);
                temp.setNext(newNodeDE);
                newNodeDE.setNext(newNodeDE);
            } else {
                headDE = new NodeDE(pet);
            }
        } catch (Exception e) {
            throw new ListDEException("An error occurred while adding a pet: " + e.getMessage());
        }
    }



    public void removeDE(@NotNull @Valid Pet pet) throws ListDEException {
        try {
            if (headDE == null) {
                throw new ListDEException("La lista está vacía");
            }

            NodeDE current = headDE;
            NodeDE prev = null;

            while (current != null) {
                if (current.getData().equals(pet)) {
                    // Found the node to remove
                    if (prev == null) {
                        // Removing the head node
                        headDE = current.getNext();
                        if (headDE != null) {
                            headDE.setPrevious(null);
                        }
                    } else {
                        // Removing a node that is not the head
                        prev.setNext(current.getNext());
                        if (current.getNext() != null) {
                            current.getNext().setPrevious(prev);
                        }
                    }
                    size--;
                    return;
                }

                prev = current;
                current = current.getNext();
            }

            throw new ListDEException("El elemento a eliminar no se encuentra en la lista");
        } catch (ListDEException ex) {
            throw new ListDEException("No puedo remover al perro intenta de nuevo"+ex.getMessage());
        }
    }

    //---------------------------------CODIGO 1 INVERTIR LA LISTA-------------------------------------------------
    public void getInvertPet() throws ListDEException {
        try {
            if (this.headDE == null) {
                throw new ListDEException("No hay elementos en la lista para invertir.");
            }
            NodeDE temp = null;
            NodeDE current = this.headDE;
            while (current != null) {
                temp = current.getPrevious();
                current.setPrevious(current.getNext());
                current.setNext(temp);
                current = current.getPrevious();
            }
            if (temp != null) {
                this.headDE = temp.getPrevious();
            }
        } catch (ListDEException e) {
            throw new ListDEException("Se ha producido un error al invertir la lista: " + e.getMessage());
        }
    }
    public List<Pet> print() {
        List<Pet> pets = new ArrayList<>();
        if (headDE != null) {
            NodeDE current = headDE;
            while (current != null) {
                pets.add(current.getData());
                current = current.getNext();
            }
        }
        return pets;
    }





    public void insertFront(Pet data) {
        NodeDE newNode = new NodeDE(data);
        if (headDE == null) {
            headDE = newNode;
        } else {
            newNode.setNext(headDE);
            headDE.setPrevious(newNode);
            headDE = newNode;
        }
        size++;
    }

    //-----------------------------CODIGO 2 MASCOTA AL INCIO Y MASCOTAS AL FINAL-------------------------------

    /*
    En este codigo (2) vemos que lo que cambio es el head por headDE y el tem para diferenciar le añadi una "o"
    Tambien vemos que lo que cambio en la lista doblemente enlazada son las excepciones que son ListSEExeption.
     */

    public void getOrderPetsToStart() throws @NotEmpty ListDEException {
        try {
            if (this.headDE != null) {
                ListDE listDE = new ListDE();
                NodeDE temp = this.headDE;

                while (temp != null) {
                    if (temp.getData().getGender() == 'M') {
                        listDE.addToStart(temp.getData());
                    } else {
                        listDE.add(temp.getData());
                    }
                    temp = temp.getNext();
                }
                this.headDE = listDE.getHead();
            } else {
                throw new @NotEmpty ListDEException("No hay niños para completar esta operacion");
            }
        } catch (ListDEException e) {
            // manejo de la excepción aquí
            // por ejemplo, puedes imprimir el mensaje de error
            throw new @NotEmpty ListDEException("Se ha producido un error al ordenar la lista de niños: " + e.getMessage());
        }
    }
    /*
    En esta parte vemos que cambiamos el Kid por el pet ya que el kid pues lo usamos para la lista simplemente
    Enlazada,Tambien convertimos ese head por headDE ya que como tenemos una clase creada como el el NodeDE tenemos cread
    el headDE para diferenciar
     */
    public void addToStart(@NotNull @Valid Pet pet) throws ListDEException {
        try {
            if (headDE != null) {
                NodeDE newNode = new NodeDE(pet);
                newNode.setNext(headDE);
                headDE.setPrevious(newNode);
                headDE = newNode;
            } else {
                headDE = new NodeDE(pet);
            }
        } catch (IllegalArgumentException e) {
            throw new ListDEException("Error: " + e.getMessage());
        }
    }



    //----------------------CODIGO 3 INTERCALAR MASCOTA (MASCULINO)-MASCOTAS( FEMENINO)-MASCOTA(MASCULINO)-MASCOTAS(FEMENINO)-------------------------------
    public void getAlternatePets() throws @NotEmpty ListDEException {
        try {
            ListDE alternateList = new ListDE();
            ListDE listBoys = new ListDE();
            ListDE listGirls = new ListDE();

            NodeDE temp = headDE;

            if (this.headDE == null && this.headDE.getNext() == null) {
                throw new @NotEmpty ListDEException("No existen niños o no hay suficientes para alternar");
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

                NodeDE boysNode = listBoys.getHead();
                NodeDE girlsNode = listGirls.getHead();

                while (boysNode != null) {
                    if (boysNode != null) {
                        alternateList.add(boysNode.getData());
                        boysNode.setPrevious(alternateList.getTail());
                        boysNode = boysNode.getNext();
                    }
                    if (girlsNode != null) {
                        alternateList.add(girlsNode.getData());
                        girlsNode.setPrevious(alternateList.getTail());
                        girlsNode = girlsNode.getNext();
                    }
                }
                this.headDE = alternateList.getHead();
            }
        } catch (NullPointerException e) {
            throw new @NotEmpty ListDEException("Error de puntero nulo al tratar de alternar niños");
        }
    }

    private NodeDE getTail() {
        return null;
    }

    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LA MASCOTA DE LA EDAD DADA -----------------

    public void removePetByAge(@Min(value = 1, message = "La edad debe ser mayor que cero") Byte age) throws ListDEException {
        NodeDE temp = headDE;
        NodeDE current = this.headDE;
        NodeDE previous = null;
        ListDE listcopy = new ListDE();
        try {
            if (this.headDE == null) {
                throw new ListDEException("No existen niños para realizar la operación");
            } else {

                while (temp != null) {
                    if (temp.getData().getAge() != age) {
                        listcopy.addToStart(temp.getData());
                    }
                    temp = temp.getNext();
                }
                this.headDE = listcopy.getHead();
                while (current!=null){
                    current.setPrevious(previous);
                    previous=current;
                    current=current.getNext();
                }

            }
        } catch (ListDEException e) {
            throw new ListDEException("Error al remover niños por edad: " + e.getMessage());
        }
    }


    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LAS MASCOTAS DE LA LISTA -------------------

    public int getLength() {
        int count = 0;
        NodeDE current = headDE;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public float getAveragePetAge() throws @NotEmpty ListDEException {
        try {
            if (headDE != null) {
                NodeDE temp = headDE;
                int count = 0;
                int age = 0;
                while (temp.getNext() != null) {
                    count++;
                    age = age + temp.getData().getAge();
                    temp = temp.getNext();
                }
                return (float) age / count;
            } else {
                throw new @NotEmpty ListDEException("No hay niños para poder hacer el promedio de edades");
            }
        } catch (ArithmeticException e) {
            throw new @NotEmpty ListDEException("No se pudo calcular el promedio de edades debido a una excepción aritmética: " + e.getMessage());
        }
    }

    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTAS MASCOTAS HAY DE CADA CIUDAD-----------------

    public void getReportPetsByLocationGendersByAge(@Min(value = 0, message = "La edad debe ser mayor o igual a cero") byte age, @NotNull ReportKidsLocationGenderDTO report) throws ListDEException {
        try {
            if (headDE != null) {
                NodeDE temp = this.headDE;
                while (temp != null) {
                    if (temp.getData().getAge() > age) {
                        report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                    }
                    temp = temp.getNext();
                }
            } else {
                throw new ListDEException("No existen niños para poder realizar la función");
            }
        } catch (ListDEException e) {
            throw new ListDEException("Error al generar el reporte de niños por ubicación y género: " + e.getMessage());
        }
    }

    public int getCountPetsByLocationCode(@NotNull String code) throws ListDEException {
        int count = 0;
        try {
            if (this.headDE != null) {
                NodeDE temp = this.headDE;
                while (temp != null) {
                    if (temp.getData().getLocation().getCode().equals(code)) {
                        count++;
                    }
                    temp = temp.getNext();
                }
            } else {
                throw new ListDEException("No existen niños para poder realizar la operación");
            }
        } catch (ListDEException e) {
            throw new ListDEException("No puedo hacer esta operacion intentalo de nuevo " + e.getMessage());
        }
        return count;
    }

    public int getCountPetsByDeptCode(@NotNull String code) throws ListDEException {
        try {
            // Llamada al método que realiza el conteo de niños en un departamento específico
            return count(code);
        } catch (ListDEException e) {
            throw new ListDEException("No se puede identificar la cantidad de niños " + e.getMessage());
        }
    }

    private int count(String code) throws ListDEException {
        int count = 0;
        if (this.headDE != null) {
            NodeDE temp = this.headDE;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        } else {
            throw new ListDEException("No existen niños para poder realizar la operación");
        }
        return count;
    }

    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UNA MASCOTA DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

    public void winPositionPet(@NotEmpty String id, @Positive int position,@NotNull ListDE listDE) throws ListDEException {

        try {
            if (!this.headDE.getData().getId().equals(id)) {
                throw new ListDEException("No existe el niño que busca");
            }

            if (headDE != null) {
                NodeDE temp = this.headDE;
                int count = 0;

                while (temp != null && !temp.getData().getId().equals(id)) {
                    temp = temp.getNext();
                    count++;
                }

                if (count > size || count < size) {
                    throw new ListDEException("No se puede realizar la accion por falta de niños");
                }

                int newPosition = count - position;
                Pet listCopy = temp.getData();
                listDE.deleteKidByIdentification(temp.getData().getId());
                listDE.addKidsByPosition(listCopy, newPosition);
                NodeDE prev = temp.getPrevious();
                NodeDE curr = temp.getNext();
                if (prev != null) {
                    prev.setNext(curr);
                } else {
                    listDE.setHeadDE(curr);
                }
                temp.setPrevious(listDE.getTail());
                temp.setNext(null);
                listDE.getTail().setNext(temp);
                listDE.setTail(temp);

                if (curr != null) {
                    curr.setPrevious(prev);
                } else {
                    listDE.setTail(prev);
                }
            } else {
                throw new ListDEException("No existen niños para poder realizar la función");
            }
        } catch (ListDEException e) {
            throw new ListDEException("Error en el método winPositionKid(): " + e.getMessage());
        }
    }

    private void setTail(NodeDE prev) {
    }

    public void addKidsByPosition(@NotNull Pet pet, int pos) throws ListDEException {
        NodeDE newNode = new NodeDE(pet);
        try {
            if (pos == 0) {
                newNode.setNext(headDE);
                if (headDE != null) {
                    headDE.setPrevious(newNode);
                }
                headDE = newNode;
            } else {
                NodeDE current = headDE;
                for (int i = 1; i < pos - 1; i++) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                newNode.setPrevious(current);
                if (current.getNext() != null) {
                    current.getNext().setPrevious(newNode);
                }
                current.setNext(newNode);
            }

        } catch (Exception e) {
            throw new ListDEException("No se puede agregar el niño en la posición indicada: " + e.getMessage());
        }
    }
    public void deleteKidByIdentification(@NotEmpty String  identification) throws ListDEException {
        try {
            NodeDE temp = headDE;
            NodeDE prevoious  = null;
            while ((temp != null) && (!temp.getData().getId().equals(identification))) {
                prevoious = temp;
                temp = temp.getNext();
            }
            if (temp != null) {
                if (prevoious == null) {
                    headDE = temp.getNext();
                } else {
                    prevoious.setNext(temp.getNext());
                }
            } else {
                throw new ListDEException("No se encontró al niño con la identificación " + identification);
            }
        } catch (ListDEException e) {
            throw new ListDEException("No se pudo eliminar al niño con la identificación " + identification + ": " + e.getMessage());
        }
    }

    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UNA MASCOTA DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void addPetAtPosForLose(@Valid Pet pet, int pos2) throws ListDEException {
        try {
            NodeDE temp = headDE;
            NodeDE newNode = new NodeDE(pet);
            int listLength = getLength();
            if (pos2 < 0 || pos2 >= listLength)//to do a validation and add the Pet in the last position
                add(pet);
            if (pos2 == 0) {
                newNode.setNext(headDE);//to actualize the head
                headDE = newNode;
                headDE.getNext().setPrevious(newNode);
                newNode.setPrevious(temp);
                newNode.getNext().setPrevious(newNode);
            } else {
                for (int i = 0; temp.getNext() != null && i < pos2 - 1; i++) {
                    temp = temp.getNext();
                }
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
            //NullPointerException: si la lista está vacía.
            //IndexOutOfBoundsException: si la posición ingresada está fuera del rango válido.
            //Exception (cualquier otra excepción): si se produce algún otro error
            // al intentar agregar la mascota.
        } catch (NullPointerException e) {
            throw new ListDEException("La lista está vacía");
        } catch (IndexOutOfBoundsException e) {
            throw new ListDEException("La posición ingresada está fuera del rango válido");
        } catch (Exception e) {
            throw new ListDEException("Se produjo un error al intentar agregar la mascota");
        }
    }

    //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------

    public void getAgeByRangePet(@Valid byte minAgepet, @Valid byte maxAgepet) throws ListDEException {
        try {
            NodeDE current = headDE;
            boolean found = false;
            while (current != null) {
                byte edad = current.getData().getAge();
                if (edad >= minAgepet && edad <= maxAgepet) {
                    String name = current.getData().getName();
                    // Aquí puedes hacer lo que quieras con los datos del niño encontrado
                    found = true;
                }
                current = current.getNext();
            }
            if (!found) {
                throw new ListDEException("No se encontraron los perros dentro del rango de edad especificado.");
            }
        } catch (NullPointerException e) {
            throw new ListDEException("La lista está vacía");
        } catch (IllegalArgumentException e) {
            throw new ListDEException("La edad mínima no puede ser mayor que la edad máxima");
        }
    }

    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LAS MASCOTAS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void addToStartNameCharPet(@NotNull char letter) throws ListDEException {
        try {
            // Verificar si la lista está vacía
            if (headDE == null) {
                throw new ListDEException("La lista está vacía");
            }

            // Inicializar variables de nodo
            NodeDE prev = null;
            NodeDE current = headDE;
            NodeDE last = null;

            // Recorrer la lista de nodos
            while (current != null) {
                // Verificar si el nombre del nodo empieza con el caracter dado
                if (current.name.startsWith(String.valueOf(letter))) {
                    // Eliminar el nodo actual de la lista
                    if (prev == null) {
                        headDE = current.getNext();
                    } else {
                        prev.setNext(current.getNext());
                        current.setPrevious(prev);
                    }

                    // Agregar el nodo actual a la lista nueva
                    if (last == null) {
                        last = current;
                    } else {
                        last.setNext(current);
                        current.setPrevious(last);
                        last = current;
                    }

                    // Avanzar al siguiente nodo
                    current = current.getNext();
                    last.setNext(null);
                } else {
                    // Avanzar al siguiente nodo
                    prev = current;
                    current = current.getNext();
                }
            }
        } catch (NullPointerException e) {
            throw new ListDEException("La lista está vacía");
        }
    }
//------------METODOS QUE TENGO QUE AÑADIR PARA LOS DIFERENTES CODIGOS -----------------------
        public void getReportPetsByLocationGendersByAge(byte age, ReportPetsLocationGenderDTO reports)throws ListDEException {
    }
}








