package co.edu.umanizales.tads.model;
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
            //PEGGUNTO CABEZA ES DIFERENTE DE NULO
            if (headDE != null) {
                //NODO TEMPORAL SERIA LO MISMO QUE LA CABEZA
                NodeDE temp = headDE;
                //EL SIGUIENTE DEL NODO TEMPORAL ES DIFERENTE DE NULO
                while (temp.getNext() != null) {
                    //HACE UNA COMPARACION CON LOS DATOS DEL NODO A LA IDENTIFICACION
                    if (temp.getData().getId().equals(pet.getId())) {
                        //EXCEPCION
                        throw new ListDEException("Ya se añadio la mascota no puedes escribirlo de nuevo");
                    }
                    //NODO TEMPORAL SERIA IGUAL A EL SIGUIENTE
                    temp = temp.getNext();
                }
                //HACE UNA COMPARACION CON LOS DATOS DEL NODO A LA IDENTIFICACION
                if (temp.getData().getId().equals(pet.getId())) {
                    throw new ListDEException("No puede volver a escribir lo mismo ");
                }
                //EL NUEVO NODO
                NodeDE newNode = new NodeDE(pet);
                //EL NODO TEMPORAL CAMBIARIA EL NUEVO NODO COMO SU SIGUIENTE
                temp.setNext(newNode);
                //EL NUEVO NODO TENDRA UN PREVIO CAMBIANDO CON SI NODO TEMPORAL
                newNode.setPrevious(temp);
            } else {
                //CABEZA ESL IGUAL AL NODO
                headDE = new NodeDE(pet);

            }
        } catch (Exception e) {
            throw new ListDEException("Problema para añadir el perro tenga cuidado: " + e.getMessage());
        }
        size++;

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
                    //BUSCA EL NODO A REMOVER
                    if (prev == null) {
                        // REMOVIENDO LA CABEZA DEL NODO
                        headDE = current.getNext();
                        if (headDE != null) {
                            headDE.setPrevious(null);
                        }
                    } else {
                        // REMOVIENDO EL NODO SI NO ES LA CABEZA
                        prev.setNext(current.getNext());
                        if (current.getNext() != null) {
                            current.getNext().setPrevious(prev);
                        }
                    }
                    size--;
                    return;
                }
                //NODO PREVIO SRIA IGUAL AL NODO ACTUAL
                prev = current;
                //NODO ACTUAL SERIA LA PARTE SIGUINETE DEL NODO
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
            //INSTANCIA ES DIFERENTE DE NULO
            if (this.headDE == null) {
                //EXCEPCION
                throw new ListDEException("No hay elementos en la lista para invertir.");
            }
            //LISTA AÑADIDA COMO LISTA INVERTIDA
            ListDE invertedList = new ListDE();
            //NODO ACTUAL SERIA LA INSTACIA
            NodeDE current = this.headDE;
            //NODO ACTUAL DIFERENTE DE NULO
            while (current != null) {
                //LISTASE AÑADE AL COMIENZO LOS DATOS DEL NODO ACTUAL
                invertedList.addToStart(current.getData());
                //LISTA ACTUAL SERIA EL SIGUIENTE DEL NODO ACTUAL
                current = current.getNext();
            }
            //INSTANCIA TOMA EL VALOR DE LA CABEZA DE LA LISTA
            this.headDE = invertedList.headDE;
        } catch (ListDEException e) {
            throw new ListDEException("Se ha producido un error al invertir la lista: " + e.getMessage());
        }
    }
/*
EL METODO DEVUELVE UNA LISTA DOBLEMENTE ENLAZADAS Y DEVUELVE UNA LISTA CON LOS OBJETOS PET
 */
    public List<Pet> print() {
        //LISTA DE LA CLASE
        List<Pet> pets = new ArrayList<>();
        //SE PREGUNTA SI LA CABEZA ES DIFERENTE DE NULO
        if (headDE != null) {
            //NODO ACTAUL ES IGUAL A LA CABEZA
            NodeDE current = headDE;
            //NODO ACTUAL ES DIFERENTE DE NULO
            while (current != null) {
                //LA LISTA ME AÑADA LOS DATOS DEL NODO ACTUAL
                pets.add(current.getData());
                //EL NODO ACTUAL SERIA EL SIGUIENTE DEL NODO ACTUAL
                current = current.getNext();
            }
        }
        return pets;
    }

    public void insertFront(Pet data) {
        //CREO UN NODO LLAMADO NUEVO NODO CON CLASE DATA
        NodeDE newNode = new NodeDE(data);
        if (headDE == null) {
            headDE = newNode;
        } else {
            //ENLACES CON EL NUEVO NODO Y LA CABEZA
            newNode.setNext(headDE);
            headDE.setPrevious(newNode);
            headDE = newNode;
        }
        //TAMAÑO AÑADE EN 1
        size++;
    }

    //-----------------------------CODIGO 2 MASCOTA AL INCIO Y MASCOTAS AL FINAL-------------------------------

    /*
    En este codigo (2) vemos que lo que cambio es el head por headDE y el tem para diferenciar le añadi una "o"
    Tambien vemos que lo que cambio en la lista doblemente enlazada son las excepciones que son ListSEExeption.
     */

    public void getOrderPetsToStart() throws ListDEException {
        //LA CABEZA ES DIFERENTE DE NULO
        if (headDE != null) {
            //SE AÑADE LA LISTA
            ListDE listDE = new ListDE();
            //NODO TEMPORAL IGUAL A LA INSTANCIA
            NodeDE temp = this.headDE;
            //TEMPORAL ES DIFERENTE DE NULO
            while (temp != null) {
                //LOS DATOS DEL NODO EN EL GENERO QUE LE ASIGNE LA LTRA
                if (temp.getData().getGender() == 'M') {
                    //LOS DATOS DE LA LISTA QUE AÑADA AL INICIO LOS DATOS DE EL NODO ACTUAL
                    listDE.addToStart(temp.getData());
                } else {
                    //LOS DATOS DE LA LISTA QUE AÑADA LOS DATOS DEL NODO TEMPORAL
                    listDE.add(temp.getData());
                }
                //EL NODO TEMPORAL SERIA EL SIGUINETE DEL NODO
                temp = temp.getNext();
            }
            //LA CABEZA SEA IGUAL A LA CABEZA DE LA LISTA
            headDE = listDE.getHead();
        } else {
            throw new ListDEException("No hay niños para completar esta operacion");
        }
    }

    /*
    En esta parte vemos que cambiamos el Kid por el pet ya que el kid pues lo usamos para la lista simplemente
    Enlazada,Tambien convertimos ese head por headDE ya que como tenemos una clase creada como el el NodeDE tenemos cread
    el headDE para diferenciar
     */
    public void addToStart(@NotNull @Valid Pet pet) throws ListDEException {
        try {
            //CABEZA ES DIFERENTE DE NULO
            if (headDE != null) {
                //NUEVO NODO
                NodeDE newNode = new NodeDE(pet);
                //EL NUEVO NODO CAMBIARIA CON LA CABEZA
                newNode.setNext(headDE);
                //LA CABEZA DEL PREVIOS CABIA A EL NUEVO NODO
                headDE.setPrevious(newNode);
                //CABEZA SEA EL NUEVO NODO
                headDE = newNode;
            } else {
                //CABEZA SEA IGUAL AL NUEVO NODO
                headDE = new NodeDE(pet);
            }
        } catch (NullPointerException e) {
            // Aquí puedes manejar la excepción de alguna otra manera
            throw new ListDEException("Error: " + e.getMessage());
        }
        size++;
    }


    //----------------------CODIGO 3 INTERCALAR MASCOTA (MASCULINO)-MASCOTAS( FEMENINO)-MASCOTA(MASCULINO)-MASCOTAS(FEMENINO)-------------------------------
    public void getAlternatePets() throws ListDEException {
        // VER SI LA LISTA ESTA VACIA
        if (headDE == null) {
            throw new ListDEException("La lista no contiene datos");
        }

        //SE CREA LAS LISTAS TANTO EL NIÑO COMO PARA LA NIÑA
        ListDE masculinoList = new ListDE();
        ListDE femeninoList = new ListDE();

        // Recorre la lista original y separa las mascotas en las listas correspondientes
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getGender() == 'M') {
                masculinoList.add(temp.getData());
            } else if (temp.getData().getGender() == 'F') {
                femeninoList.add(temp.getData());
            }
            temp = temp.getNext();
        }

        // Verifica si hay suficientes mascotas femeninas para realizar el sorteo
        if (femeninoList.getSize() == 0) {
            throw new ListDEException("No hay suficientes mascotas de género femenino para realizar el sorteo");
        }

        // Inicializa nodos para recorrer las listas masculina y femenina
        NodeDE masculinoNode = masculinoList.getHeadDE();
        NodeDE femeninoNode = femeninoList.getHeadDE();

        // Inicializa los nodos de la lista combinada
        NodeDE combinedListHead = null;
        NodeDE combinedListTail = null;

        // Combina los nodos de las listas masculina y femenina alternativamente
        while (masculinoNode != null && femeninoNode != null) {
            // Si es el primer nodo, establece la cabeza y la cola de la lista combinada
            if (combinedListHead == null) {
                combinedListHead = masculinoNode;
                combinedListTail = masculinoNode;
            } else {
                // Agrega el nodo masculino a la lista combinada
                combinedListTail.setNext(masculinoNode);
                combinedListTail = combinedListTail.getNext();
            }

            // Agrega el nodo femenino a la lista combinada
            combinedListTail.setNext(femeninoNode);
            combinedListTail = combinedListTail.getNext();

            // Avanza a los siguientes nodos de ambas listas
            masculinoNode = masculinoNode.getNext();
            femeninoNode = femeninoNode.getNext();
        }

        // Si quedan nodos femeninos, añádelos al final de la lista combinada
        if (femeninoNode != null) {
            combinedListTail.setNext(femeninoNode);
        }

        // Actualiza la cabeza de la lista original con la lista combinada
        headDE = combinedListHead;
    }

    //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LA MASCOTA DE LA EDAD DADA -----------------

    public void removePetByAge(@Min(value = 1, message = "La edad debe ser mayor que cero") Byte age) throws ListDEException {
        //QUE LE NODO TEMPORAL SEA LA CABEZA
        NodeDE temp = headDE;
        //LISTA COPIA
        ListDE listcopy = new ListDE();
        //SE PREGUTA SI LA EDAD ES MENOR QUE 0
        if (age <= 0) {
            throw new ListDEException("La edad debe ser mayor que cero");
        } else {
            //INSTANCIA ES IGUAL A NULO
            if (this.headDE == null) {
                throw new ListDEException("No existen niños para realizar la operación");
            } else {
                //NODO TEMPORAL DIFERENTE DE NULO
                while (temp != null) {
                    //LOS DATOS QUE TIENE EL NODO TEMPORAL ES DIFERENTE A LA EDAD
                    if (temp.getData().getAge() != age) {
                        //LISTA COPIA AÑADE A LOS DATOS
                        listcopy.add(temp.getData());
                    }
                    //NODO TEMPORAL SERIA EL SIGUIENTE DEL NODO
                    temp = temp.getNext();
                }
                //INSTANCIA ES IGUAL A LA CABEZA DE LA LISTA COPIA
                this.headDE = listcopy.getHead();

            }
        }

    }


    //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LAS MASCOTAS DE LA LISTA -------------------

    public float getAveragePetAge() throws ListDEException {
        //SI LA CABEZA ES NULA
        if (headDE == null) {
            throw new ListDEException("No hay mascotas para calcular el promedio de edades");
        }
        //NODO TEMPORAL ES IGUAL A LA CABEZA
        NodeDE temp = headDE;
        int count = 0;
        int age = 0;
        //NODO TEMPORAL ES DIFERENTE DE NULO
        while (temp != null) {
            //SUME EL CONTADOR DESPDE QUE SEA CIERTO
            count++;
            age += temp.getData().getAge();
            temp = temp.getNext();
        }
        //QUE ME RETORNE EN FLOAT EL NUMERO
        return (float) age / count;
    }


    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTAS MASCOTAS HAY DE CADA CIUDAD-----------------

    public int getCountPetsByLocationCode(String code) throws ListDEException {
        try {
            //SI EL COIGO ES IGUAL A NULO O EL CODIGO YA ESTA VACIO
            if (code == null || code.isEmpty()) {
                throw new ListDEException("El codigo que escribio no puede estar vacio tenga cuidado ");
            }
            //VARIABLE INICIALIZADA EN 0
            int count = 0;
            //INSTANCIA ES DIFERENTE DE NULO
            if (this.headDE != null) {
                //EL NODO ACTUAL ES LA INSTANCIA
                NodeDE current = this.headDE;
                //EL NODO ACTUAL ES DIFERENTE DE NULO
                while (current != null) {
                    //SE COMPARA LA IDENTIFICACION
                    if (current.getData().getLocation().getCode().equals(code)) {
                        count++;
                    }
                    //NODO ACTUAL SERIA EL SIGUINETE DEL NODO ACTUAL
                    current = current.getNext();
                }
            }
            //RETORNE EL CONTADOR
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
        //VARIABLES INCIALIZADAS EN 0
        int count = 0;
        //INSTACIA ES DIFERENTE DE NULO
        if (this.headDE != null) {
            //NODO TEMPORAL ES IGUAL A LA INSTANCIA
            NodeDE temp = this.headDE;
            //NODO TEMPORAL ES DIFERENTE DE NULO
            while (temp != null) {
                //LISTAR LA CANTIDAD DE DATOS TENIENDO EN CUENTA LA VARIABLE CON LA LOCALIZACION DADA
                if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {
                    //SUMAR EL CONTADOR SI ESS VERDADERO
                    count++;
                }
                //NODO TEMPORAL SERIA EL SIGUINETE DEL NODO TEMPORAL
                temp = temp.getNext();
            }
        } else {
            throw new ListDEException("No existen niños para poder realizar la operación");
        }
        return count;
    }

    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UNA MASCOTA DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------
    public void winPetPosition(String id, int position) throws ListDEException {
        // Verificar si la posición es negativa
        if (position < 0) {
            throw new ListDEException("La posición debe ser un número positivo");
        }

        // Verificar si la lista no está vacía
        if (headDE != null) {
            NodeDE temp = headDE;
            int counter = 0;

            // Buscar el nodo con el ID especificado
            while (temp != null && !temp.getData().getId().equals(id)) {
                temp = temp.getNext();
                counter++;
            }

            // Verificar si se encontró el nodo con el ID especificado
            if (temp != null) {
                Pet listCopy = temp.getData();
                deleteById(temp.getData().getId());

                // Insertar el nodo al comienzo de la lista
                addByPosition(listCopy, 1);
            }
        }
    }

    public void addToStartPet(@NotNull(message = "El objeto pet no puede ser nulo") Pet pet) throws ListDEException {
        //CLASE PET ES IGUAL A NULO
        if (pet == null) {
            throw new ListDEException("El objeto pet no puede ser nulo");
        }
        //AÑADIENDO EL NODO
        NodeDE nodeDE = new NodeDE(pet);
        //CABEZA ES DIFERENTE DE NULO
        if (headDE != null) {
            //LA CABEZA DEL ANTERIOR SERIA EL NODO
            headDE.setPrevious(nodeDE);
            //EL NODO EL SIGUINETE SERIA LA CABEZA
            nodeDE.setNext(headDE);
        }
        //CABEZA ES IGUAL AL NODO
        headDE = nodeDE;
        size++;
    }
//------------------------AÑADIR EN POSICION---------------------

//ESTE CODIGO AÑADE MAS DE UNA POSICION POR EJEMPLO 40
    public void addByPosition(Pet pet, int position) throws ListDEException {
        //SI LA POSICION ES MENOR QUE CERO O LA POSICION ES IGUAL A LA TALLA +1 POSICION IGUAL A EL TAMAÑO
        if (position <= 0 || position > size + 1) {
            position = size + 1;
        }
        //NODO TEMPORAL SEA IGUAL A LA CABEZA Y CONTADOR INICIALIZO EN 1
        NodeDE temp = headDE;
        int count = 1;
        //SI EL CONTADOR ES MENOR QUE LA POSICION -1Y LA MANO DERECHA DEL NODO ES DIFERENTE QUE LA CABEZA
        while (count < position - 1 && temp.getNext() != headDE) {
            //NODO TEMPORAL ES IGUAL A EL NODO DE LA MANO DERECHA CONTADOR AUMENTA EN 1
            temp = temp.getNext();
            count++;
        }
        //CREA UN NUEVO NODO CON CLASE PET
        NodeDE newNode = new NodeDE(pet);
        //LA MANO DERECHA DEL NUEVO NODO SERIA LA MANO DERECHA DEL NUEVO NODO
        newNode.setNext(temp.getNext());
        //LA MANO IZQUIERDA DEL NUEVO NODO SERA EL NODO TEMPORAL
        newNode.setPrevious(temp);
        //SI LA MANO DERECHA DEL NODO TEMPORAL ES DIFERENTE DE NULO
        if (temp.getNext() != null) {
            //EL NODO TEMPORAL CON SU PREVIO AGARRE AL NUEVO NODO O BUENO NEW NODE
            temp.getNext().setPrevious(newNode);
        }
        //NODO TEMPORAL LA MANO DERECHA DEL NODO CAMBIARIA EL NUEVO NODO
        temp.setNext(newNode);
        //EL TAMAÑO SE AÑADE EN 1
        size++;
    }


    public void addToEnd(Pet pet) {
        //SE CREA EL NUEVO NODO CON UNA CLASE PET
        NodeDE newNode = new NodeDE(pet);
        //SI LA CABEZA ESTA VACIA
        if (headDE == null) {
            //CABEZA ES NUEVO NODO
            headDE = newNode;
        } else {
            //EL NODO TEMPORAL SERIA LA CABEZA
            NodeDE temp = headDE;
            //LA MANO DERECHA DEL NODO TEMPORAL ES DIFERENTE DE NULO
            while (temp.getNext() != null) {
                //NODO TEMPORAL SERA IGUAL AL LA MANO DERECHA DEL NODO
                temp = temp.getNext();
            }
            //LA MANO DERECHA DEL NODO TEMPORAL ES IGUAL AL NUEVO NODO
            temp.setNext(newNode);
            //LA MANO IZQUIERDA DEL NUEVO NODO SERIA TEMPORAL
            newNode.setPrevious(temp);
        }
        //TAMAÑO AUMENTA EN 1
        size++;
    }


    public void deleteById(@NotNull String id) throws ListDEException {
        //SI EL ID ES NULO
        if (id == null) {
            throw new ListDEException("El identificador del dueño no puede ser nulo");
        }
        //NODO TEMPORAL SERIA LA CABEZA
        NodeDE temp = headDE;
        //EL NODO TEMPORAL ES DIFERENTE DE NULO
        while (temp != null) {
            //COMPARAR LA IDENTIFICACION DE EL NODO TEMPORAL
            if (temp.getData().getId().equals(id)) {
                //EL NODO PREVIO SERIA EL PREVIO DE EL NODO TEMPORAL
                NodeDE prev = temp.getPrevious();
                //EL NODO SIGUIENTE SERIA EL SIGUINETE DE EL NODO TEMPORAL
                NodeDE next = temp.getNext();
                //SI EL NODO PREV ES IGUAL A NULO
                if (prev == null) {
                    //CABEZA IGUAL AL NODO SIGUINETE
                    headDE = next;
                } else {
                    //NODO PREV SIGUINETE SERIA EL NODO SIGUINETE
                    prev.setNext(next);
                }
                //EL NODO SIGUINETE ES DIFERENTE DE NULO
                if (next != null) {
                    //EL NODO SIGUINETE QUE EL ANTERIOR O EL PREVIO SEA EL NODO ANTERIOR
                    next.setPrevious(prev);
                }
            }
            //EL NODO TEMPORAL SERIA EL SIGUINETE DEL NODO TEMPORAL
            temp = temp.getNext();
        }
    }


    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UNA MASCOTA DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS---------------
    public void losePetPosition(String id, int position) throws ListDEException {
        //POSICION ES NEGATIVA
        if (position < 0) {
            throw new ListDEException("La posición debe ser positiva");
        }
        //CREAMOS UN AYUDANTE Y UN CONTADOR PARA QUE LO PONGA EN LA POSCICION INDICADA
        NodeDE temp = headDE;
        int count = 1;

        // Buscar la mascota con el ID especificado
        while (temp != null && !temp.getData().getId().equals(id)) {
            temp = temp.getNext();
            count++;
        }
        //NODO AYUDANTE NO TIENE DATOS

        if (temp == null) {
            throw new ListDEException("No se encontró una mascota con el ID especificado");
        }
        //NEVA POSICION ES SUMARKE AL CONTADOR LA POSICION

        int newPosition = count + position;
        if (newPosition < 0) {
            throw new ListDEException("La posición resultante es inválida");
        }

        // Eliminar la mascota de la posición actual
        if (temp == headDE) {
            headDE = temp.getNext();
        } else {
            temp.getPrevious().setNext(temp.getNext());
            if (temp.getNext() != null) {
                temp.getNext().setPrevious(temp.getPrevious());
            }
        }

        // Obtener la mascota eliminada y agregarla en la nueva posición
        addByPosition(temp.getData(), newPosition);
    }


    //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------

    public int getRangePetByAge(int startAge, int finishAge) throws ListDEException {
        if (startAge < 0 || finishAge < 0) {
            throw new ListDEException("El informe de rangos no puede ser negativo");
        }
        //NODO TEMPORAL ES IGUAL AL NODO
        NodeDE temp = headDE;
        int counter = 0;
        //EL NODO TEMPORAL ES DIFERENTE DE NULO
        while (temp != null) {
            //LOS DATOS DE EL NODO TEMPORAL ES MAYOR QUE LA EDAD AL COMIENZO O LA OTRA PARTE CONTRARIA
            if (temp.getData().getAge() >= startAge && temp.getData().getAge() <= finishAge) {
                //SI SE CUMPLE CONTADOR SE SUMA EN 1
                counter++;
            }
            //NODO TEMPORAL ES IGUAL A EL SIGUIENTE DEL NODO
            temp = temp.getNext();
        }
        //RETORNE CONTADOR

        return counter;
    }


    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LAS MASCOTAS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----------

    public void sendPetToTheEndByLetter(@NotNull Character letter) throws ListDEException {
        try {
            // Verificar si la lista no está vacía
            if (this.headDE != null) {
                // Crear una lista copia para almacenar los elementos
                ListDE listCopy = new ListDE();

                // Nodo temporal para recorrer la lista original
                NodeDE temp = this.headDE;

                // Convertir la letra recibida a mayúscula
                char firstChar = Character.toUpperCase(letter);

                // Recorrer la lista original
                while (temp != null) {
                    // Obtener la primera letra del nombre del animal
                    char firstLetter = temp.getData().getName().charAt(0);

                    // Comparar la primera letra con la letra recibida
                    if (Character.toUpperCase(firstLetter) != firstChar) {
                        // Si no coincide, agregar el elemento al principio de la lista copia
                        listCopy.addToStartPet(temp.getData());
                    } else {
                        // Si coincide, agregar el elemento al final de la lista copia
                        listCopy.add(temp.getData());
                    }
                    temp = temp.getNext();
                }

                // Actualizar la cabeza de la lista original con la cabeza de la lista copia,instancia
                this.headDE = listCopy.getHeadDE();
            } else {
                // Si la lista está vacía, lanzar una excepción
                throw new ListDEException("La lista no puede estar vacía");
            }
        } catch (NullPointerException ex) {
            // Si el argumento 'letter' es nulo, lanzar una excepción
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
    public void removeNodeByIdentificationPet(String identification) throws ListDEException {
        // Verificar si la lista está vacía
        if (headDE == null) {
            throw new ListDEException("La lista está vacía.");
        }

        // Buscar el nodo con la identificación especificada
        NodeDE current = headDE;
        while (current != null && !current.getData().getId().equals(identification)) {
            current = current.getNext();
        }

        // Verificar si no se encontró el nodo con la identificación especificada
        if (current == null) {
            throw new ListDEException("No se encontró un nodo con la identificación especificada.");
        }

        // Actualizar enlaces
        NodeDE previous = current.getPrevious();
        NodeDE next = current.getNext();

        if (previous != null) {
            previous.setNext(next);
        } else {
            headDE = next;
        }

        if (next != null) {
            next.setPrevious(previous);
        }
    }



}













