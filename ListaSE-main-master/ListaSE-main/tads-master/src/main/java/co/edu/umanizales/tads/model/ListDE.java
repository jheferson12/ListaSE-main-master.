package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.*;

import javax.swing.*;
import javax.validation.constraints.NotNull;

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

    public void add(Pet pet) {
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

    }

    public void removeDE(Pet pet) {
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
            System.out.println(ex.getMessage());
        }
    }


    //---------------------------------CODIGO 1 INVERTIR LA LISTA-------------------------------------------------
    public void invertPet(@NotNull Pet pet) throws ListDEException {
        try {
            if (this.headDE != null) {
                ListDE listDE = new ListDE();
                NodeDE temp = this.headDE;
                while (temp != null) {
                    listDE.insertFront(temp.getData());
                    temp = temp.getNext();
                }
                this.headDE = listDE.getHead();
            } else {
                throw new ListDEException("La lista está vacía");
            }
        } catch (ListDEException e) {
            JOptionPane.showMessageDialog(null, "Error al invertir la lista de mascotas: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Aquí mostramos un cuadro de diálogo con el mensaje de error al usuario
        }
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

    //-----------------------------CODIGO 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------

    /*
    En este codigo (2) vemos que lo que cambio es el head por headDE y el tem para diferenciar le añadi una "o"
    Tambien vemos que lo que cambio en la lista doblemente enlazada son las excepciones que son ListSEExeption.
     */
    public void getOrderPetsToStart() {
        try {
            if (this.headDE != null) {
                ListDE listDE = new ListDE();
                NodeDE tempo = this.headDE;
                NodeDE lastBoy1 = null;
                while (tempo != null) {
                    if (tempo.getData().getGender() == 'M') {
                        if (lastBoy1 != null) {
                            listDE.addToStart(lastBoy1.getData());
                        }
                        lastBoy1 = tempo;
                    } else {
                        listDE.add(tempo.getData());
                    }
                    tempo = tempo.getNext();
                }
                if (lastBoy1 != null) {
                    listDE.addToStart(lastBoy1.getData());
                }
                this.headDE = listDE.getHead();
            } else {
                throw new ListDEException("La lista está vacía");
            }
        } catch (ListDEException e) {
            System.out.println("Se lanzó una excepción de ListDE: " + e.getMessage());
            // aquí podrías hacer algo más con la excepción, como registrarla en un archivo de registro o mostrar un mensaje de error al usuario
        } catch (Exception e) {
            System.out.println("Se lanzó una excepción inesperada: " + e.getMessage());
            // aquí podrías hacer algo más con la excepción, como registrarla en un archivo de registro o mostrar un mensaje de error al usuario
        }
    }


    /*
    En esta parte vemos que cambiamos el Kid por el pet ya que el kid pues lo usamos para la lista simplemente
    Enlazada,Tambien convertimos ese head por headDE ya que como tenemos una clase creada como el el NodeDE tenemos cread
    el headDE para diferenciar
     */
    public void addToStart(Pet pet) {
        if (pet == null) {
            return;
        }
        if (headDE != null) {
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(headDE);
            headDE = newNode;
        } else {
            headDE = new NodeDE(pet);
        }
    }

    //----------------------CODIGO 3 INTERCALAR NIÑO-NIÑA-NIÑO-NIÑA-------------------------------
    public void getAlternatePets() throws ListDEException {
        try {
            if (headDE == null || headDE.getNext() == null) {
                throw new ListDEException("La lista está vacía o solo tiene un elemento");
            }

            NodeDE petsMa = headDE;
            NodeDE petsFe = headDE.getNext();
            NodeDE petsFe1 = petsFe;

            while (petsFe != null && petsMa != null) {
                petsMa.setNext(petsFe.getNext());
                if (petsMa.getNext() != null) {
                    petsFe.setNext(petsFe.getNext().getNext());
                }
                petsMa = petsMa.getNext();
                petsFe = petsMa.getNext();
            }

            if (petsFe == null) {
                petsMa.setNext(petsFe1);
            } else {
                petsFe.setNext(petsFe1);
            }
        } catch (ListDEException e) {
            throw new ListDEException("La lista está vacía");
            // aquí podrías hacer algo más con la excepción, como registrarla en un archivo de registro o mostrar un mensaje de error al usuario
        } catch (Exception e) {
            throw new ListDEException("La lista está vacía");
            // aquí podrías hacer algo más con la excepción, como registrarla en un archivo de registro o mostrar un mensaje de error al usuario
        }
    }

    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA -----------------

    public void removePetByAge(byte age) throws ListDEException {
        try {
            if (age <= 0) {
                throw new ListDEException("La edad debe ser un valor positivo mayor que cero");
            }
            NodeDE current = headDE;
            NodeDE prev = null;
            while (current != null) {
                if (current.getData().getAge() == age) {
                    if (prev == null) {
                        headDE = current.getNext();
                    } else {
                        prev.setNext(current.getNext());
                    }
                } else {
                    prev = current;
                }
                current = current.getNext();
            }
        } catch (NullPointerException e) {
            throw new ListDEException("La lista está vacía");
        }
    }

    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LOS NIÑOS DE LA LISTA -------------------

    public int getLength() {
        int count = 0;
        NodeDE current = headDE;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListSEException {
        try {
            double averageAge = 0;
            NodeDE temp = this.headDE;
            if (this.headDE != null) {
                while (temp != null) {
                    averageAge = averageAge + temp.getData().getAge();
                    temp = temp.getNext();
                }
                averageAge = averageAge / getLength();
                return averageAge;

            } else {
                throw new ListSEException("La lista está vacía");
            }
        } catch (ArithmeticException e) {
            throw new ListSEException("La lista está vacía");
        }
    }

    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD-----------------

    public int getCountPetsByLocationCode(String code) throws ListSEException {
        try {
            if (code == null || code.isEmpty()) {
                throw new ListSEException("El código de ubicación no puede ser nulo o vacío");
            }
            int count = 0;
            if (this.headDE != null) {
                NodeDE temp = this.headDE;
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
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ListSEException("Ha ocurrido un error en la lista: " + e.getMessage());
        }
    }

    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

    public void winPositionPet(String id, int win) throws ListDEException {
        try {
            NodeDE temp = headDE;
            int sum = 0;
            ListDE listDE = new ListDE();
            int size = getLength();

            if (headDE != null) {
                while (temp != null && !temp.getData().getId().equals(id)) {
                    listDE.add(temp.getData());
                    temp = temp.getNext();
                }

                if (temp == null) {
                    throw new ListDEException("No se encontró una mascota con el ID " + id);
                }

                sum = temp.getData().getPosition() + win;

                if (sum < 0) {
                    throw new ListDEException("No se puede mover la mascota más allá de la primera posición");
                } else if (sum > size) {
                    throw new ListDEException("No se puede mover la mascota más allá de la última posición");
                }

                listDE.add(new Pet(temp.getData().getId(), temp.getData().getName(), sum));
                temp = temp.getNext();

                while (temp != null) {
                    listDE.add(temp.getData());
                    temp = temp.getNext();
                }

                headDE = listDE.getHead();
            } else {
                throw new ListDEException("La lista está vacía");
            }
        } catch (ListDEException e) {
            throw new ListDEException(e.getMessage());
        }
    }

    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------

    public void addPetAtPosForLose(Pet pet, int pos2) throws ListDEException {
        try {
            NodeDE temp = headDE;
            NodeDE newNode = new NodeDE(pet);
            int listLength = getLength();
            if (pos2 < 0 || pos2 >= listLength)//to do a validation and add the kid in the last position
                add(pet);
            if (pos2 == 0) {
                newNode.setNext(headDE);//to actualize the head
                headDE = newNode;

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

    public void getAgeByRangePet(byte minAgepet, byte maxAgepet) throws ListDEException {
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

    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void addToStartNameChar(char letter) throws ListSEException {
        try {
            // Verificar si la lista está vacía
            if (headDE == null) {
                throw new ListSEException("La lista está vacía");
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
                    }

                    // Agregar el nodo actual a la lista nueva
                    if (last == null) {
                        last = current;
                    } else {
                        last.setNext(current);
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
            throw new ListSEException("La lista está vacía");
        }
    }
}








