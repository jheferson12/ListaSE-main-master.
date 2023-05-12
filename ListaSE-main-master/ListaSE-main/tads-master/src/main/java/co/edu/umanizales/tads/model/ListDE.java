package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
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
                    if (temp.getData().getId().equals(pet.getId())) {
                        throw new ListDEException("Ya se añadio la mascota no puesdes escribirlo de nuevo");
                    }
                    temp = temp.getNext();
                }
                if (temp.getData().getId().equals(pet.getId())) {
                    throw new ListDEException("No puede volver a escribir lo mismo ");
                }
                NodeDE newNode = new NodeDE(pet);
                temp.setNext(newNode);
                newNode.setPrevious(temp);
            } else {
                headDE = new NodeDE(pet);

            }
        } catch (Exception e) {
            throw new ListDEException("Problema para añadir el perro tenga cuidado: " + e.getMessage());
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
            throw new ListDEException("No puedo remover al perro intenta de nuevo" + ex.getMessage());
        }
    }

    //---------------------------------CODIGO 1 INVERTIR LA LISTA-------------------------------------------------
    public void getInvertPet() throws ListDEException {
        try {
            if (this.headDE == null) {
                throw new ListDEException("No hay elementos en la lista para invertir.");
            }
            ListDE listDE = new ListDE();
            NodeDE current = this.headDE;
            while (current != null) {
                listDE.addToStart(current.getData());
                current = current.getNext();
            }
            if (current != null) {
                this.headDE = current.getPrevious();
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
            if (headDE != null) {
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
                headDE = listDE.getHead();
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
            ListDE listBoys = new ListDE();
            ListDE listGirls = new ListDE();
            ListDE listDE = new ListDE();
            NodeDE boysNode = listBoys.getHead();
            NodeDE girlsNode = listGirls.getHead();


            NodeDE temp = headDE;

            if (headDE == null) {
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
                while (boysNode != null || girlsNode != null) {
                    if (boysNode != null) {
                        listDE.add(boysNode.getData());
                        boysNode = boysNode.getNext();

                    }
                    if (girlsNode != null) {
                        listDE.add(girlsNode.getData());
                        girlsNode = girlsNode.getNext();
                    }
                }
                this.headDE = listDE.getHead();
            }
        } catch (NullPointerException e) {
            throw new @NotEmpty ListDEException("Error de puntero nulo al tratar de alternar niños");
        }
    }

    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LA MASCOTA DE LA EDAD DADA -----------------

    public void removePetByAge(@Min(value = 1, message = "La edad debe ser mayor que cero") Byte age) throws ListDEException {
        NodeDE temp = headDE;
        NodeDE previous = temp.getPrevious();
        NodeDE next = temp.getNext();

        try {
            if (temp.getData().getAge() == age) {
                throw new ListDEException("No existen niños para realizar la operación");
            } else {

                while (temp != null) {
                    if (temp.getData().getAge() != age) {
                        if (previous == null) {
                            headDE = next;
                        } else {
                            previous.setNext(next);
                        }
                        if (next == null) {
                            next.setPrevious(previous);

                        }
                    }
                    temp = temp.getNext();
                }

            }
        } catch (ListDEException e) {
            throw new ListDEException("Error al remover niños por edad: " + e.getMessage());
        }
    }


    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LAS MASCOTAS DE LA LISTA -------------------

    public float getAveragePetAge() throws @NotEmpty ListDEException {
        try {
            if (headDE != null) {
                NodeDE temp = headDE;
                int count = 0;
                int age = 0;
                while (temp.getNext() != null) {
                    count++;
                    age += temp.getData().getAge();
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

    public int getCountPetsByLocationCode(String code)throws ListDEException {
        try {
            if (code == null || code.isEmpty()) {
                throw new ListDEException("El codigo que escribio no puede estar vacio tenga cuidado ");
            }
            int count = 0;
            if (this.headDE != null) {
                NodeDE current = this.headDE;
                while (current != null) {
                    if (current.getData().getLocation().getCode().equals(code)) {
                        count++;
                    }
                    current = current.getNext();
                }
            }
            return count;
        } catch (ListDEException e) {
            throw new ListDEException("Error: " + e.getMessage());

        }
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

    public void winPositionPet(@NotNull @NotEmpty String id, @PositiveOrZero int position) throws ListDEException {
        try {
            if (position < 0) {
                throw new ListDEException("La posición debe ser un número positivo");
            }
            if (headDE != null) {
                NodeDE temp = headDE;
                int counter = 1;
                while (temp != null && !temp.getData().getId().equals(id)) {
                    temp = temp.getNext();
                    counter++;
                }
                if (temp != null) {
                    int newPosition = counter - position;
                    if (newPosition < 0) {
                        throw new ListDEException("La posición especificada está fuera de los límites de la lista");
                    }
                    Pet listCopy = temp.getData();
                    deleteById(temp.getData().getId());
                    if (newPosition > 0) {
                        addByPosition(listCopy, newPosition);
                    } else {
                        addToStartPet(listCopy);
                    }
                }
            }
        } catch (ListDEException e) {
            throw new ListDEException("Error: " + e.getMessage());
        }
    }


    public void addToStartPet(@NotNull(message = "El objeto pet no puede ser nulo")Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("El objeto pet no puede ser nulo");
        }

        NodeDE nodeDE = new NodeDE(pet);
        if (headDE != null) {
            headDE.setPrevious(nodeDE);
            nodeDE.setNext(headDE);
        }
        headDE = nodeDE;
        size++;
    }

    public void addByPosition(@NotNull Pet pet,@Min(0) int position) throws ListDEException {
        try {
            if (position < 0 || position > size) {
                throw new ListDEException("Posicion invalida:" + position);
            }
            NodeDE newNode = new NodeDE(pet);
            if (position == 0) {
                newNode.setNext(headDE);
                if (headDE != null) {
                    headDE.setPrevious(newNode);
                }
                headDE = newNode;
            } else {
                NodeDE current = headDE;
                for (int i = 1; i < position - 1; i++) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                if (current.getNext() != null) {
                    current.getNext().setPrevious(newNode);
                }
                current.setNext(newNode);
                newNode.setPrevious(current);
            }
        } catch (Exception e) {
            throw new ListDEException("Error occurred while adding node: " + e.getMessage());
        }
    }



    public void deleteById(@NotNull String id) throws ListDEException {
        if (id == null) {
            throw new ListDEException("El identificador del dueño no puede ser nulo");
        }

        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getId().equals(id)) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNext();
                if (prev == null) {
                    headDE = next;
                } else {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrevious(prev);
                }
            }
            temp = temp.getNext();
        }
    }


    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UNA MASCOTA DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void losePositionPet(@NotNull String id,@Positive int positionpet) throws ListDEException {
        try {
            if (positionpet < 0) {
                throw new ListDEException("La posicion debe ser positiva");
            }
            NodeDE temp = headDE;
            int count = 1;
            while (temp != null && !temp.getData().getId().equals(id)) {
                temp = temp.getNext();
                count++;
            }

            int sum = positionpet + count;
            Pet pet = temp.getData();
            deleteById(temp.getData().getId());
            addByPosition(pet, sum);
        } catch (ListDEException e) {
            throw new ListDEException("Error: " + e.getMessage());
        }
    }

    //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------

    public int getRangePetByAge(@PositiveOrZero int Startpet,@PositiveOrZero  int finishpet) throws ListDEException {
        try {
            if (Startpet < 0 || finishpet < 0) {
                throw new ListDEException("El informe de rangos no puede ser negativo");
            }
            NodeDE temp = headDE;
            int counter = 0;
            while (temp != null) {
                if (temp.getData().getAge() >= Startpet && temp.getData().getAge() <= finishpet) {
                    counter++;
                }
                temp = temp.getNext();
            }
            return counter;
        } catch (ListDEException e) {
            throw new ListDEException("Error: " + e.getMessage());
        }
    }

    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LAS MASCOTAS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void sendPetToTheEndByLetter(@NotNull Character letter) throws ListDEException {
        try {
            if (this.headDE != null) {
                ListDE listCopy = new ListDE();
                NodeDE temp = this.headDE;
                char firstChar = Character.toUpperCase(letter);

                while (temp != null) {
                    char firstLetter = temp.getData().getName().charAt(0);
                    if (Character.toUpperCase(firstLetter) != firstChar) {
                        listCopy.addToStartPet(temp.getData());
                    } else {
                        listCopy.add(temp.getData());
                    }
                    temp = temp.getNext();
                }
                this.headDE = listCopy.getHeadDE();
            } else {
                throw new ListDEException("La lista no puede estar vacia");
            }
        } catch (NullPointerException ex) {
            throw new ListDEException("El argumento letter no puede ser nulo");
        }
    }

    /*
    Eliminar por Identificacion
    Cuando queremos eliminar a un niño tenienndo en cuenta la identificacion lo primero que ago es lo sigiente
    1Comienzo a crear un metodo  que me pida como tal la identificacion  por ejemplo deleteByidentification(String identification).
    2 depues en esta parte le pido en el caso que Recorra la listaDE que
    la creo con este nombre para ver el tema del nodo que en ese caso le pongo el valor llamado temp
    que corresponde a esa identificacion  teniendo en cuenta esa parte.Una  vez que encuentre el nodo
    creado en este caso NodeDE, que me establezca tanto el getNext y el getPrevious  y siguiente para apuntar el uno al otro, omitiendo el nodo que se va a eliminar.
    Y me comienzo a preguntar lo siguiente,Si el nodo que se va a eliminar es el primer nodo en la lista, establezca la cabeza de la lista en el siguiente nodo.
    Si el nodo que se va a eliminar es el último nodo en la lista  es decir en este caso el getprevious ,
    establezca la cola de la lista en el nodo anterior.
    Devuelve el nodo que se eliminó de la lista.
     */
    //---------------------EJERCICIO 8/05/23----------------------------------------
    public NodeDE removeNodeByIdentificationPet(@NotNull String identification)throws ListDEException {
           try {
               NodeDE temp = headDE;
               NodeDE previousNode = null;

               while (temp != null) {
                   if (temp.getName().equals(identification)) {
                       if (previousNode != null) {
                           previousNode.setNext(temp.getNext());
                       } else {
                           headDE = temp.getNext();
                       }

                       if (temp.getNext() != null) {
                           temp.getNext().setPrevious(previousNode);
                       }

                       temp.setPrevious(null);
                       temp.setNext(null);

                       return temp;
                   }

                   previousNode = temp;
                   temp = temp.getNext();
               }
           } catch (Exception e) {
               throw new ListDEException("Error occurred while removing node: " + e.getMessage());
           }

           return null;
    }

}








