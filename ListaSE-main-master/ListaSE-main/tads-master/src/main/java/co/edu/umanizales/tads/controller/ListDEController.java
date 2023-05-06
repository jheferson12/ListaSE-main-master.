package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ReportPetsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.*;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
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
        @GetMapping(path = "getpets")
        public ResponseEntity<ResponseDTO> getPets() {
            try {
                return new ResponseEntity<>(new ResponseDTO(
                        200, listDEService.getPets().getHead(), null), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Error al obtener la lista de mascotas" + e.getMessage(),
                        null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //----------------AÑADIR MASCOTA------------------
        @PostMapping(path = "/addpet")
        public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) throws ListSEException {
            Location location = locationService.getLocationsByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            try {
                listDEService.getPets().add(
                        new Pet(petDTO.getIdentificationPet(), petDTO.getName(), petDTO.getAge(), petDTO.getGender(), petDTO.getCodeLocation()));

            } catch (ListDEException e) {
                return new ResponseEntity<>(new ResponseDTO(
                        409, e.getMessage(),
                        null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el petacón",
                    null), HttpStatus.OK);

        }

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
        @GetMapping("/invertpet")
        public ResponseEntity<ResponseDTO> getInvertPet(@RequestParam @NotNull(message = "El parámetro pet no puede ser nulo") Pet pet) {
            try {
                listDEService.getPets().getInvertPet();
                return new ResponseEntity<>(new ResponseDTO(
                        200, "Se ha invertido la lista",
                        null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al invertir la lista: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(200, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //-----------------------------CONTROLLER 2 MASCOTA (MASCULINO)AL INCIO Y MASCOTAS (FEMENINO) AL FINAL-------------------------------
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
        private ResponseEntity<Object> handleListDEException(String errorMessage, Exception ex) {
            return new ResponseEntity<>(errorMessage + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //-----------------CONTROLLER CON GETMAPPING-----------------------------
        @GetMapping(path = "/orderpetstostart")
        public ResponseEntity<ResponseDTO> getOrderPetsToStart(@NotNull ListDE listDE) throws ListDEException {
            try {
                listDEService.getPets().getOrderPetsToStart();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha intercambiado" +
                        " la mascota al inicio y las mascotas al final ", null), HttpStatus.OK);
            } catch (Exception ex) {
                String errorMessage = "Error en ordenar la mascota/s al comienzo: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(200, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
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
        @GetMapping(path = "/alternatepets")
        public ResponseEntity<ResponseDTO> getAlternatePets(@NotNull NodeDE headDE) {
            try {
                listDEService.getPets().getAlternatePets();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha alternado la lista",
                        null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al alternar la lista: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (IllegalStateException ex) {
                String errorMessage = "Ocurrió un estado ilegal: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
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
        @GetMapping(path = "/removepetbyagepet{age}")
        public ResponseEntity<ResponseDTO> removePetByAge(@Min(value = 1, message = "La edad  de la masocta/s debe ser mayor que cero") Byte age) {
            try {
                listDEService.getPets().removePetByAge(age);
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha eliminado el perro de " + age + " años de edad", null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al eliminar el perro de " + age + " años de edad: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
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


        @GetMapping(path = "/averageagepet")
        public ResponseEntity<ResponseDTO> getAveragePetAge(@Valid byte age) {
            try {
                double averageAge = listDEService.getPets().getAveragePetAge();
                return ResponseEntity.ok(new ResponseDTO(200, "Promedio de edad de la mascota/s: " + averageAge, null));
            } catch (Exception ex) {
                String errorMessage = "Error al calcular el promedio de edad de las mascota/s: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS PERROS HAY DE CADA CIUDAD CODIGO A-----------------
        @ExceptionHandler(value = ListDEException.class)
        public ResponseEntity<Object> handleListSEException(ListDEException ex) {
            try {
                String errorMessage = "Error al generar un reporte de mascota/s por ciudad " + ex.getMessage();
                return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                return new ResponseEntity<>("Error inesperado al manejar la excepción: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //-------------REPORT GETMAPPING------------------------
        @GetMapping(path = "/get_count_pets_by_location_code")
        @ExceptionHandler(value = {ListSEException.class})
        public ResponseEntity<ResponseDTO> getCountPetsByLocationCode(@NotNull String code) throws ListDEException {
            try {
                listDEService.getPets().getCountPetsByLocationCode(code);
                return new ResponseEntity<>(new ResponseDTO(200, "ya esta identificado por ciudad la mascota/s", null), HttpStatus.OK);
            } catch (Exception ex) {
                String errorMessage = "Error al obtener el conteo de niños por código de ubicación: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



        //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS PERROS HAY DE CADA CIUDAD CODIGO B-----------------
        @ExceptionHandler(ListDEException.class)
            public ResponseEntity<String> handle(ListDEException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede identificar la cantidad de mascota/s " + ex.getMessage());
    }


        //--------------------CON GETMAPING------------------------------------------
        @GetMapping(path = "/petsbylocationgenders/{age}")
        public ResponseEntity<ResponseDTO> getReportPetsByLocationGendersByAge(@PathVariable byte age) {
            try {
                ReportPetsLocationGenderDTO reports =
                        new ReportPetsLocationGenderDTO(locationService.getLocationsByCodeSize(age));
                listDEService.getPets().getReportPetsByLocationGendersByAge(age, reports);
                return new ResponseEntity<>(new ResponseDTO(
                        200, reports,
                        null), HttpStatus.OK);
            } catch (ListDEException exception) {
                String errorMessage = "Error al generar el reporte de mascota/s por género y ciudad: " + exception.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS PERROS HAY DE CADA CIUDAD CODIGO C----------------
    @ExceptionHandler(ListDEException.class)
    public ResponseEntity<String> handleListDEException(ListDEException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede identificar la cantidad de mascota/s " + ex.getMessage());
    }


        //------------------CON GET MAPPING--------------------------------------
        @GetMapping("/countpetsByDept")
        public ResponseEntity<ResponseDTO> getCountPetsByDeptCode(@NotNull String code) {
            try {
                int count = listDEService.getPets().getCountPetsByDeptCode(code);
                return new ResponseEntity<>(new ResponseDTO(200, "Cantidad de mascota/s en el departamento: " + count, null), HttpStatus.OK);
            } catch (ListDEException e) {
                String errorMessage = "Error al obtener la cantidad de mascota/s: " + e.getMessage();
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
        @GetMapping(path = "/winPositionpet/{id}/{win}")
        public ResponseEntity<ResponseDTO> winPositionPet(@NotEmpty String id, @Positive int position, @NotNull ListDE listDE) {
            try {
                listDEService.getPets().winPositionPet(id, position, listDE);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "la mascota/s gano la posicion propuesta  ", null), HttpStatus.OK);
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
            return new ResponseEntity<>("Tiene un error al pedir a la mascota/s perder el numero de posciciones",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



        //-------------------CODIGO CON POSTMAPPING----------------------------
        @PostMapping(path = "/pets/addpetsatposforlose/{pos}")
        public ResponseEntity<ResponseDTO> addPetAtPosForLose(@Valid Pet pet, int pos2) {
            try {
                listDEService.getPets().addPetAtPosForLose(pet, pos2);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "mascota/s agregado exitosamente en la posición " + pos2, null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al agregar el niño en la posición " + pos2 + ": " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------
        @ExceptionHandler(value = {ListDEException.class, IllegalArgumentException.class})
        public ResponseEntity<Object> handleException(Exception ex) {
            try {
                return new ResponseEntity<>("No se a tenido en cuenta el rango de edad de las mascota/s",HttpStatus.INTERNAL_SERVER_ERROR);
            }catch (IllegalStateException stateException){
                return new ResponseEntity<>("No se a visto la mascota/s añadido ",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //----------CONTROLLER CON GETMAPPING------------------------------
        @GetMapping(path = "/getagebyrange/{age}")
        public ResponseEntity<ResponseDTO> getAgeByRangePet(@Valid byte minAgepet, @Valid byte maxAgepet) {
            try {
                listDEService.getPets().getAgeByRangePet(minAgepet, maxAgepet);
                return new ResponseEntity<>(new ResponseDTO(200, "Este es rango por edades de las mascota/s", null), HttpStatus.OK);
            } catch (ListDEException ex) {
                return new ResponseEntity<>(new ResponseDTO(500, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL
        // FINAL DE LA LISTA A LOS PERROS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----
        @ExceptionHandler(value = IllegalArgumentException.class)
        public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
           try {
               return new ResponseEntity<>("No se encuentra la mascota/s añadido ",HttpStatus.INTERNAL_SERVER_ERROR);
           }catch (IllegalStateException illegalStateException){
               return new ResponseEntity<>("No puedo enviar a ningun/a mascota/s de la lista,No se encuentra ",HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }


        //----------------------CODIGO GETMAPPING------------------------------
        @GetMapping(path = "/addtostartnamechar/{letter}")
        public ResponseEntity<ResponseDTO> addToStartNameCharPet(@NotNull char letter) {
            try {
                listDEService.getPets().addToStartNameCharPet(letter);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "el nombre  de la mascota/s fue agregado al principio de la lista", null), HttpStatus.OK);
            } catch (ListDEException ex) {
                String errorMessage = "Error al agregar el nombre  de la mascota/s al principio de la lista: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception ex) {
                String errorMessage = "Se ha producido un error inesperado: " + ex.getMessage();
                return new ResponseEntity<>(new ResponseDTO(500, errorMessage, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


    }




















