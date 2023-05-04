package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.ReportPetsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.NodeDE;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/lists2")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @ControllerAdvice
    public class ListDEExceptionHandler {
        //---------------------------------CONTROLLER 1 INVERTIR LA LISTA-----------------------------------------------
        //-------Voy ha hacer una combinacion etre el controller advice y el controller con el get y post ------
        //--------CON CONTROLLER ADVICE----------------
        @ExceptionHandler(value = {ListDEException.class})
        public ResponseEntity<Object> handleListDEException(ListDEException ex, WebRequest request) {
            try {
                String errorMessage = "Error al invertir la lista: " + ex.getMessage();
                return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                String errorMessage = "Ocurrió un error inesperado: " + e.getMessage();
                return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //----------------CON CONTROLLER GETMAPPING-----------
        @GetMapping("/invert")
        public ResponseEntity<ResponseDTO> getInvertPet(@RequestParam @NotNull Pet pet) {
            try {
                listDEService.getPets().getinvertPet(pet);
                return new ResponseEntity<>(new ResponseDTO(
                        200, "Se ha invertido la lista",
                        null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al invertir la lista: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(200,errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //-----------------------------CONTROLLER 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------
        @ExceptionHandler(value = {ListDEException.class})
        public ResponseEntity<Object> handleListDEException(ListDEException ex) {
           try {
               String errorMessage="Error al tener perro a al inicio"+ex.getMessage();
               return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
           }catch (Exception exception){
               String errorMessage="Error al tener perr@s al final porque no se encuentra agregado "+exception.getMessage();
               return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }
        private ResponseEntity<Object> handleException(String errorMessage, Exception ex) {
            return new ResponseEntity<>(errorMessage + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //-----------------CONTROLLER CON GETMAPPING-----------------------------
        @GetMapping(path = "/orderboystostart")
        public ResponseEntity<ResponseDTO> getOrderPetsToStart(@NotNull ListDE listDE) {
            try {
                listDEService.getPets().getOrderPetsToStart(listDE);
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha intercambiado" +
                        " los niños al inicio y niñas al final ", null), HttpStatus.OK);
            } catch (ListSEException ex) {
                String errorMessage = "Error en ordenar los perros al comienzo: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(200,errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //----------------------CODIGO 3 INTERCALAR PETM-PETF-PETM-PETF-------------------------------

        @ExceptionHandler(value = {NoSuchElementException.class})
        public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
            try {
                return new ResponseEntity<>("No se encontró el elemento solicitado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>("Ha ocurrido un error al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //------------------CONTROLLER CON GETMAPPING--------------------------
        @GetMapping(path = "/alternatekids")
        public ResponseEntity<ResponseDTO> getAlternatePets(@NotNull NodeDE headDE) {
            try {
                listDEService.getPets().getAlternatePets(headDE);
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha alternado la lista",
                        null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al alternar la lista: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500,errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (IllegalStateException ex) {
                String errorMessage = "Ocurrió un estado ilegal: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500,errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS PERROS DE LA EDAD DADA -----------------
        @ExceptionHandler(value = {IllegalStateException.class})
        public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
            try {
                return new ResponseEntity<>("Ocurrió un estado ilegal: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //------------------CODIGO CON GETMAPPING---------------------------
        @GetMapping(path = "/removepetbyage")
        public ResponseEntity<ResponseDTO> removePetByAge(@Min(0) @Max(14) byte age) {
            try {
                listDEService.getPets().removePetByAge(age);
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha eliminado el perro de " + age + " años de edad", null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al eliminar el perro de " + age + " años de edad: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500,errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LOS PERROS DE LA LISTA -------------------
        @ExceptionHandler(value = {FileNotFoundException.class})
        public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
            try {
                // Manejo del error
                return new ResponseEntity<>("El archivo no fue encontrado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                // Manejo de cualquier otro error que pueda ocurrir durante la ejecución del método
                return new ResponseEntity<>("Error al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
        @GetMapping(path = "/averageage")
        public ResponseEntity<ResponseDTO> getAveragePetAge(@Valid byte age) {
            try {
                double averageAge = listDEService.getPets().getAveragePetAge(age);
                return ResponseEntity.ok(new ResponseDTO(200, "Promedio de edad: " + averageAge, null));
            } catch (ListSEException ex) {
                String errorMessage = "Error al calcular el promedio de edad de las mascotas: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500,errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



        //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS PERROS HAY DE CADA CIUDAD-----------------
        @ExceptionHandler(value = ListDEException.class)
        public ResponseEntity<Object> handleListSEException(ListDEException ex) {
            try {
                String errorMessage = "Error al generar un reporte de perros por ciudad " + ex.getMessage();
                return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                return new ResponseEntity<>("Error inesperado al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        //-------------REPORT GETMAPPING------------------------
        @GetMapping(path = "/get_count_kids_by_location_code")
        @ExceptionHandler(value = {ListSEException.class})
        public ResponseEntity<ResponseDTO> getCountPetsByLocationCode(@Valid String code) {
            try {
                listDEService.getPets().getCountPetsByLocationCode(code);
                return new ResponseEntity<>(new ResponseDTO(200, "ya esta identificado por ciudad el niño", null), HttpStatus.OK);
            } catch (ListSEException ex) {
                String errorMessage = "Error al obtener el conteo de niños por código de ubicación: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportPetsByLocationGendersByAge(@PathVariable byte age) {
        try {
            ReportPetsLocationGenderDTO reports =
                    new ReportPetsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
            listDEService.getPets().getReportPetsByLocationGendersByAge(age, reports);
            return new ResponseEntity<>(new ResponseDTO(
                    200, reports,
                    null), HttpStatus.OK);
        } catch (ListDEException exception) {
            String errorMessage = "Error al generar el reporte de mascotas por género y ciudad: " + exception.getMessage();
            return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UN PERRO DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

    @ExceptionHandler(value = {IndexOutOfBoundsException.class})
    public ResponseEntity<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException exception) {
        try {
            return new ResponseEntity<>("Se ha producido una excepción de índice fuera de rango", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException exception1) {
            return new ResponseEntity<>("Se ha producido un error al manejar la excepción de índice fuera de rango", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //----------------------------CONTROLLER CON GETMAPPING-----------------------
    @GetMapping(path = "/winPositionKid/{id}/{win}")
    public ResponseEntity<ResponseDTO> winPositionPet(@Valid String id, @Valid int win) {
        try {
            listDEService.getPets().winPositionPet(id, win);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "el niño gano la posicion propuesta  ", null), HttpStatus.OK);
        } catch (ListDEException ex) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error al tratar de ganar la posición: " + ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN PERRO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS-----------
        @ExceptionHandler(value = {NullPointerException.class})
        public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        try {
            return new ResponseEntity<>("No se ha añadido el perro ",HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NullPointerException pointerException){
            return new ResponseEntity<>("Tiene un error al pedir al perro perder el numero de posciciones",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        //-------------------CODIGO CON POSTMAPPING----------------------------
        @PostMapping(path = "/kids/addkidatposforlose/{pos}")
        public ResponseEntity<ResponseDTO> addPetAtPosForLose(@Valid Pet pet, int pos2) {
            try {
                listDEService.getPets().addPetAtPosForLose(pet, pos2);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Niño agregado exitosamente en la posición " + pos2, null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al agregar el niño en la posición " + pos2 + ": " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage,null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


    //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------
        @ExceptionHandler(value = {ListDEException.class, IllegalArgumentException.class})
        public ResponseEntity<Object> handleException(Exception ex) {
            try {
                return new ResponseEntity<>("No se a tenido en cuenta el rango de edad del perro",HttpStatus.INTERNAL_SERVER_ERROR);
            }catch (IllegalStateException stateException){
                return new ResponseEntity<>("No se a visto el perro añadido ",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        //----------CONTROLLER CON GETMAPPING------------------------------
        @GetMapping(path = "/getagebyrange/{age}")
        public ResponseEntity<ResponseDTO> getAgeByRangePet(@Valid byte minAgepet, @Valid byte maxAgepet) {
            try {
                listDEService.getPets().getAgeByRangePet(minAgepet, maxAgepet);
                return new ResponseEntity<>(new ResponseDTO(200, "Este es rango por edades", null), HttpStatus.OK);
            } catch (ListDEException ex) {
                return new ResponseEntity<>(new ResponseDTO(500, ex.getMessage(),null ), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL
        // FINAL DE LA LISTA A LOS PERROS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----
        @ExceptionHandler(value = IllegalArgumentException.class)
        public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
           try {
               return new ResponseEntity<>("No se encuentra el perro añadido ",HttpStatus.INTERNAL_SERVER_ERROR);
           }catch (IllegalStateException illegalStateException){
               return new ResponseEntity<>("No puedo enviar a ningun perro de la lista,No se encuentra ",HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }
        //----------------------CODIGO GETMAPPING------------------------------
        @GetMapping(path = "/addtostartnamechar/{letter}")
        public ResponseEntity<ResponseDTO> addToStartNameCharPet(@NotNull char letter) {
            try {
                listDEService.getPets().addToStartNameCharPet(letter);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "el nombre fue agregado al principio de la lista", null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al agregar el nombre al principio de la lista: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception ex) {
                String errorMessage = "Se ha producido un error inesperado: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage,null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


}



















