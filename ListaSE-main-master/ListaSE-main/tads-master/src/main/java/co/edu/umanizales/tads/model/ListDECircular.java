package co.edu.umanizales.tads.model;
import co.edu.umanizales.tads.exception.ListDEEExceptionCircular;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Getter
@Setter
@AllArgsConstructor


public class ListDECircular {
    private NodeDE nextfirst;
    private NodeDE previouslast;
    private NodeDE headDEcircular;
    private int size;
    private NodeDE newNode;

    public ListDECircular() {
        nextfirst = null;
        previouslast = null;
    }
    /*
        AÑADIR A LA MASCOTA
        TENIENDO EN CUENTA QUE ES UNA LISTA DOBLEMENTE ENLAZADA CIRCULAR LO QUE NECESITAMOS ES QUE TODOS QUEDEN
         AMARRADOS COMENZAMOS PREGUNTANDO EN UN CODICIONAL SI LA CABEZA ES DIFERENTE DE NULO,SI NO ES NULO SE CREA
          UN NODO TEMPORAL IGUAL A LA CABEZA.
          IMPLEMENTAMOS UN DO WHILE Y EN LA PARTE DEL DO VERIFICAMOS QUE SI EL IDENTIFICADOR DEL DATO ALMACENADO DEL
          NODO ACTUAL ES IGUAL AL IDENTIFICADOR DEL OBJETO PET DE COMIENZA A COMPARAR CON AYUDA DEL EQUALS.
          SI LA CONDICION QUE IMPLEMENTAMOS EN EL CONDICIONAL SE CUMPLE ENTONCES DAMOS UNA EXCEPCION "QUE YA
           SE AÑADIO LA MASCOTA"SIGNIFICA QUE NO SE PUEDES AGREGAR LA IDENTIFICACION NUEVAMENTE DESPUES DE SALIR CON EL CONDICIONAL
           VEMOS UN "temp=temp.getnext() LO QUE HACEMOS ES QUE SEA ASIGNADO AL SIGUIENTE NODO.
           DESPUES COMENZAMOS A VER EL WHILE Y ENTONCES IMPLEMENTAMOS QUE SI TEMPORAL ES DIFERENTE DE NULO
           ENTONCES SE CREA UN NODO TIPO NUEVO NODO "NodeDE newNode = new NodeDE(pet);" UNA VEZ QUE AÑADIMOS ESE NODO COMENZAMOS
           A ENLAZAR A TODOS TANTO CON EL SET NEXT COMO EN EL GETPREVIOUS TENEIDNO EN CUENTA QUE ES TENER COMO LA MANO DERECHA
           "getNext" Y LA IZQUIERDA "getprevious".COMENZAMOS A USAR EL NODO LLAMADO NUEVO NODO SE ESTABLECE QUE EL NODO ANTERIOR DEL
           NUEVO NODO CON EL NODO ANTERIOR DE LA CABEZA"newNode.setPrevious(headDEcircular.getPrevious());",DESPUES
           COMENZAMOS A ACTUALIZAR EL NODO SIGUIENTE DEL NODO ANTERIOR PERA QUE APUNTE AL NUEVO NODO,DESPUES SE ESTABLECE EL NUEVO NODO
           CON EL NODO ANTERIOR DE LA CABEZA HACIA NUEVO NODO DESPUES SE ESTTABLECE COMO NODO ANTERIOR LA CABEZA.
           EN LA PARTE DEL ELSE PORQUE HICIMOS EL CONDICIONAL EN LA PARTE DEL ELSE SE CREA UN NUEVO NODO LLAMADO CABEZA COMO OBJETO PET
           DESPUES SE ESTABLECE EL NODO SIGUIENTE DE LA CABEZA IGUAL A LA MISMA CABEZA,DESPUES SE ESTABLECE EL NODO ANTERIOR
           CON LA CABEZA QUE TAMBIEN SEA EL NODO CABEZA Y DESPUES DE ELLO PUES SE COMIENZA A AUMENTAR EL TAMAÑO
     */
    public void add(Pet pet) throws ListDEEExceptionCircular {

            if (headDEcircular != null) {
                NodeDE temp = headDEcircular;
                do {
                    if (temp.getData().getId().equals(pet.getId())) {
                        throw new ListDEEExceptionCircular("Ya se añadió la mascota, no puedes escribirlo de nuevo");
                    }
                    temp = temp.getNext();
                } while (temp != headDEcircular);

                NodeDE newNode = new NodeDE(pet);
                newNode.setPrevious(headDEcircular.getPrevious());
                newNode.setNext(headDEcircular);
                headDEcircular.getPrevious().setNext(newNode);
                headDEcircular.setPrevious(newNode);
            } else {
                headDEcircular = new NodeDE(pet);
                headDEcircular.setNext(headDEcircular);
                headDEcircular.setPrevious(headDEcircular);
            }
            size++;

    }

   /*
    METODO IMPRIMIR
    COMENZAMOS A CREAR UNA NUEVA LISTA LLAMADA PETS QUE ALMECENA LOS OBJETOS DE PET,DESPUES
     SE CREA UN CONDICIONAL Y SE DICE SI LA CABEZA ES DIFERENTE DE NULO SE COMIENZA A CREAR UN NUDO LLAMADO
      CURRENT=ACTUAL, UNA VEZ QUE YA CREAMOS EL NODO ENTONCES CON AYUDA DEL DO SE AGREGA EL DATO PET ALMACENANDO
      EL NODO CURRENT UTILIZANDO EL METODO ADD, UNA VEZ DE AGREGAR EL DATO SE ACTUALIZA EL NODO CURRENT PARA PASAR AL SIGUIENTE
       NODO DE LA ESTRUCTURA
     DESPUES DE HABER COMPLETADO EL BUCLE SE DEVUELVE EN LA LISTA PETS LA CUAL CONTIENE TODOS LOS OBJETOS QUE
     CONTIENE PET RECOPILADOS DURANTE EL RECORRIDO Y A LO ULTIMO SE RETORNA LA LISTA QUE ES LA QUE TIENE TODOS LOS DATOS.
    */
    public List<Pet> print() {
        List<Pet> pets = new ArrayList<>();
        if (headDEcircular != null) {
            NodeDE current = headDEcircular;
            do {
                pets.add(current.getData());
                current = current.getNext();
            } while (current != headDEcircular);
        }
        return pets;
    }
    /*ALGORITMO  AÑADIR AL INICIO
    COEMZAMOS A CREAR UN NUEVO NODO CON EL VALOR INGRESADO.
    ENTONCES TENIENDO EN CUENTA LA CONDICION ESTOS DICIENDO QUE SI LA CABEZA ES IGUAL A NULL ENTONCES QUE ME AÑADA AL PERRRO
    UNA VEZ QUE MENCIONAMOS LO ANTERIOR COMENZAMOS CON LA PARTE DEL ELSE,DENTRO DEL ELSE COMENZAMOS A CREAR UN NODO QUE SE LLAMA NUEVO NODO
    DESPUES COMENZAMOS A CREAR UN NUEVO NODO LLAMADO TEMPORAL Y SE LE ASIGNA EL VALOR DEL NODO ANTERIOR A LA CABEZA
    DESPUES SE ESTABLECES QUE EL TEMP CONO NUEVO NODO ENTONCES YA TEMP SE DISFRAZA DE NEW NODO,
    DESPUES SE ESTABLECE EL NEW NODO COMO EL NODO ANTERIOR DE LA CABEZA ENTONCES IMPLEMENTA QUE EL NUEVO NODO TENGA EL PAPEL DE LA CABEZA
    UNA VEZ QUE DESARROLLAMOS LO ANTERIOR LA CABEZA SE ACTUALIZA PARA QUE APUNTE AL NUEVO NODO Y YA CON LA LOGICA
    TOSOS ESTARIAN JUNTOS,UNA VEZ DE HACERLO PONEMOS UN SIZE LO QUE HACE ES AÑADIR 1.
     */
    public void addFirst(Pet pet)throws ListDEEExceptionCircular {
        if (headDEcircular == null) {
            add(pet);
        }else{
            NodeDE newNode = new NodeDE(pet);
            NodeDE temp = headDEcircular.getPrevious();
            temp.setNext(newNode);
            newNode.setPrevious(temp);
            newNode.setNext(headDEcircular);
            headDEcircular.setPrevious(newNode);
            headDEcircular=newNode;
            size++;
        }
    }
    /*
    ALGORITMO AÑADIR AL FINAL
    TENIENDO EN CUENTA QYUE TIENE LA MISMA LOGICA DEL METODO ANTERIOR SINO QUE YA COMIENZA A AÑADIR ALGUNAS COSAS QUE
    MAS ADELANTE LO EXPLICO.
    ENTONCES COMENZAMOS A UTILIZAR UNA CONDICION Y ME DICE QUE SI LA CABEZA ES IGUAL A NULO ENTONCES QUE LE AÑADA LA CABEZA
    EN LA PARTE DEL ELSE COMENZAMOS A CREAR UN NODO LLAMADO NEWNODE CON EL OBJETO PET COMO NUEVO DATO DESPUES SE CREA
    UN NODO LLAMADO LASTNODE LA CUAL SE LE ASIGNA EL VALOR DEL NODO ANTERIOR A LA CABEZA,DESPUES
    ESTABLECE QUE EL NODO SIGUIENTE DE LASTNODE SEA COMO NEWNODE,UNA VEZ QUE HACE LO ANTERIOR EL NEWNODE SE ESTABLECES COMO LASTNODE
    LA CUAL CONECTA A NEWNODE,DESPUES SE ESTABLECE EL NODO SIGUIENTE DEL NEWNODECOMO LA CABEZA,AHORA EL NEWNODE
    SE COLOCA DESPUES DE LASTNODE EN LA ESTRUCTURA DE LOS DATOS OBTENIDOS ,DESPUES SE ESTABLECE QUE LA NUEVA CABEZA
    SE CONVIERTE EN EL NODO ANTERIOR A LA CABEZA Y EN LA PARTE FINAL EL SIZE INCREMENTA EL TAMAÑO DE LA ESTRUCTURA QUE SE VA OBTENIENDO

     */
    public void addFinal(Pet pet)throws ListDEEExceptionCircular {
        if (headDEcircular == null) {
            add(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE lastNode = headDEcircular.getPrevious();
            lastNode.setNext(newNode);
            newNode.setPrevious(lastNode);
            newNode.setNext(headDEcircular);
            headDEcircular.setPrevious(newNode);
            size++;
        }
    }
    /*
    TAMAÑO
    ESTE METODO LO QUE HACE ES VER EL TAMAÑO DE LOS AÑADIR POR EJEMPLO USTED AÑADE UNA MAZCOTA UNA VEZ QUE LO
    LISTE ENTONCES LE VA AUMENTANDO
     */
    public int tamaño(){
        return size;
    }

//----------AÑADIR EN POSCION--------------------------

    /*
    METODO AÑADIR POSICION
    COMENZAMOS A USAR UN CONDICIONAL Y DECIMOS QUE SI LA POSICION ES IGUAL A 1 ENTONCES QUE ME AGREGE AL PRINCIPIO DE LA
    LISTA PERO EN LA PARTE DEL ELSE SE CREA UN NODO TEMPORAL O TEMP ASIGNADO A LA CABEZA,DESPUES
    COMIENZA A INICIAR EN 1 EL CONTADOR PARA LLEVAR LA CUENTA DE LA POSICION ACTUAL,DECIMOS QUE MIENTRAS CEL CONTADOR
    EN 1 SI EL NOTO TEMPORAL SIGUIENTE NO ES NULO SE CREA UN NODO NEW NODE CON EL OBJETO PET COMO DATO,DESPUES COMIENZO A
    MIRAR QUE EL SIGUIENTE SE CAMBIA AL NODO TEMPORAL PARA ESTABLECER EL NODO ANTERIOR DE NUEVO NODO,UNA VEZ QUE SE GA LO ANTERIOR
    ENTONCES QUE EL NODO TEMPORAL SIGUIENTE QUE AGARRE A NUEVO NODO DE LO CONTRARIO SE REALIZA LA EXCEPTION.

     */

    public void addByPosition(Pet pet, int position) throws ListDEEExceptionCircular {
        if (position <= 0 || position > size + 1) {
            position = size + 1;
        }

        NodeDE newNode = new NodeDE(pet);

        if (headDEcircular == null) {
            // La lista está vacía
            headDEcircular = newNode;
            newNode.setNext(newNode);
            newNode.setPrevious(newNode);
        } else {
            // La lista no está vacía
            NodeDE temp = headDEcircular;
            int count = 1;

            while (count < position - 1 && temp.getNext() != headDEcircular) {
                temp = temp.getNext();
                count++;
            }

            newNode.setNext(temp.getNext());
            newNode.setPrevious(temp);
            temp.getNext().setPrevious(newNode);
            temp.setNext(newNode);

            if (position == 1) {
                headDEcircular = newNode;
            }
        }

        size++;
    }










    //---------------BAÑAR AL PERRO---------------------------

    /*
         BAÑAR MASCOTA
        CUANDO BAÑAMOS LAS MASCOTAS NECESITAMOS ALGUNAS LIBRERIAS COMO RAMDOM Y NADA MAS.
        COMENZAMOS COMO PARAMETRO UNA LETRA LA LETRA NO VA HA SER LO QUE EMPIEZA EL NOMBRE DE LA MASCOTA AÑADIDA SINO QUE IZQ
        IZQUIERDA Y DERECHA COMO ESTAN INDICADOS MAS ABAJO COMENZAMOS A CREAR UAN ARIABLE DE TIPO CHAR Y NECESITO DE TODO LO
        LO QUE INGRESE ME CONVIERATA EN MINUSCULA YA QUE LO TENGO ESPECIFICADO MAS ABAJO O BUENO EN EL CONDICIONAL DE LA PARTE DE ABAJO
        COMENZAMOS A VERIFICAR SI NO HAY PERROR PARA BAÑAR ES DECIR SI EL NODO TEMPORAL ES DECIR EL QUE AÑADIMOS ES IGUAL A NULO
        ENTONCES QUE ME RETORNE NULO.UNA VEZ QUE INDICAMOS LA PARTE DEL CONDICIONAL VERIFICAMOS SI LA LETRA INGRESADA COMO ESTA MENCIONADA EN EL CODI
        CONDICIONAL ESTA BIEN DIGITADA,DESPUES COMENZAMOS A APLICAR LA LIBREARIA RANDOM QUE EN EL COMIENZO LES MENSIONE.
        DESPUES COMENZAMOS A CREAR UNA VARIABLE NUM Y UNA VARIABLE COUNT QUE INICIALIZE EN 1 DESPUES USAMOS OTRO CONDICIONAL
        Y DECIMOS SI EMPIEZA EN DE ENTONCES QUE EL NODO TEMPORAL SEA EL SIGUEINTE DEL NODO TEMPORAL DESPUES APLICAMOS EL MIENTRAS
        Y DECIMOS SI COUNT QUE ES LA VARIABLE QUE MENCIONE ANTERIORMENTE  ES DIFERENTE A LA VARIABLE NUM  ENTONCES QUE EL NODO
        TEMP SEA IGAUL AL NODO SIGUIENTE Y ENTONCES QUE EL CONADOR COMIENZE A SUMAR MAS DATOS.
        DESPUES COMENZAMOS HA HACER LA CONDICION SI LOS DATOS DEL NODO TEMPORAL NO SON VERDADEROS ENTONCES UE ME RETORNE VERDADEROS
        O SI NO PUES QUE ME RETORNE NULO.

     */
    public Pet takeShower(char letter) {
        char start = Character.toLowerCase(letter);
        NodeDE temp = headDEcircular;

        // Verificar si no hay perros para bañar
        if (temp == null) {
            return null;
        }

        // Verificar si la letra ingresada es válida ('d' o 'i')
        if (start != 'd' && start != 'i') {
            return null;
        }

        Random rand = new Random();
        int num = rand.nextInt(size) + 1;
        int count = 1;

        if (start == 'd') {
            temp = temp.getNext();
            while (count != num) {
                temp = temp.getNext();
                count++;
            }
            if (!temp.getData().isBathdog()) {
                temp.getData().setBathdog(true); // Bañar la mascota
                return temp.getData(); // Devolver la mascota bañada
            } else {
                return null;
            }
        } else {
            temp = temp.getPrevious();
            while (count != num) {
                temp = temp.getPrevious();
                count++;
            }
            if (!temp.getData().isBathdog()) {
                temp.getData().setBathdog(true); // Bañar la mascota
                return temp.getData(); // Devolver la mascota bañada
            } else {
                return null;
            }
        }
    }
    /*
    OBTENER PRIMERO
    LO QUE HACE ES QUE PREGUNTA CON UNA CONDICION Y DICE SI LA CABEZA ES DIFERENTE DE NULO ENTONCES UE ME RETORNE LOS DATOS
    DE LA CABEZA O SI NO PUES QUE RETORNE NULO
     */
    public Pet getFirst() {
        if (headDEcircular != null) {
            return headDEcircular.getData();
        }
        return null;
    }
    /*
        OBTENER NUMERO MASCOTA
    EN ESTE METODO SE INICIALIZA LA VARIABLE TEMP CON EL VALOR DE LA CABEZA Y QUE  SU CONTADOR QUEDE EN 1
    PREGUTAMOS SI TEMP NO ES NULO CON AYUDA DEL DO WHILE HACER SI LOS DATOS DE TEMP SON IGUALES A TEMP ES DECIR AL NODO
    CREADO ENTONCES QUE ME RETORNE CONTADOR,DESPUES DE ESO QUE AVANCE TEMP AL SIGUIENTE NODO Y DESPUES QUE ME INCREMENTE COUNT EN 1
    MIENTRAS TEMP NO SEA IGUAL A CABEZA QUE ME RETORNE -1
     */
    public int getPetNumber(Pet pet) {
        NodeDE temp = headDEcircular;
        int count = 1;

        if (temp != null) {
            do {
                if (temp.getData() == pet) {
                    return count;
                }
                temp = temp.getNext();
                count++;
            } while (temp != headDEcircular);
        }

        return -1; // Si no se encuentra la mascota en la lista
    }




















}
