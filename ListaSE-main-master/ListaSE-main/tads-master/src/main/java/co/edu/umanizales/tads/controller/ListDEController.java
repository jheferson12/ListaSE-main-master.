package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.*;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangePetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/lists2")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangePetService rangePetService;

    @ControllerAdvice
    public class ListDEExceptionHandler {
        //---------------------------------CONTROLLER 1 INVERTIR LA LISTA-----------------------------------------------
        @GetMapping(path = "getpets")
        public ResponseEntity<ResponseDTO> getPets() {
            try {
                return new ResponseEntity<>(new ResponseDTO(
                        200, listDEService.getPets().print(), null), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Error al obtener la lista de mascotas" + e.getMessage(),
                        null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //----------------AÑADIR MASCOTA------------------
        @PostMapping(path = "/addpet")
        public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO)  {
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
                    200, "Se ha adicionado la mascota que ingreso",
                    null), HttpStatus.OK);

        }
        //---------------------------BORRAR POR ID DE LA MASCOTA-----------
        @GetMapping(path = "/deletebyid/{id}")
        public ResponseEntity<ResponseDTO> deleteById(@PathVariable String id)  {
            try {
                listDEService.getPets().deleteById(id);
                return new ResponseEntity<>(new ResponseDTO(
                        200, "La/s mascota/s fue/ron borrada/os felicitaciones", null), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Tiene un error al eliminar la/s mascota/s", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        //-------------------------------AÑADIR EN POSICION A LA MASCOTA-------------------------
        @GetMapping(path = "/addbyposition/{position}")
        public ResponseEntity<ResponseDTO> addByPosition(@RequestBody Pet pet, @PathVariable int position)  {
            try {
                listDEService.getPets().addByPosition(pet, position);
                return new ResponseEntity<>(new ResponseDTO(
                        200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Se produjo un error al agregar la mascota en la posición solicitada", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //-------Voy ha hacer una combinacion etre el controller advice y el controller con el get y post ------
        //--------CON CONTROLLER ADVICE----------------
        @ExceptionHandler(value = {ListDEException.class})
        public ResponseEntity<Object> handleListDEExecptionPet(ListDEException ex) {
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
        public ResponseEntity<ResponseDTO> getInvertPet()  {
            try {
                if (listDEService.getPets() != null) {
                    listDEService.getPets().getInvertPet();
                    return new ResponseEntity<>(new ResponseDTO(200,
                            "La lista de mascotas se invertio correctamente", null), HttpStatus.OK);

                } else {
                    return new ResponseEntity<>(new ResponseDTO(409,
                            "No se puede invertir a la mascota tenga cuidado", null), HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(500,
                        "Error por el servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        //-----------------------------CONTROLLER 2 MASCOTA (MASCULINO)AL INCIO Y MASCOTAS (FEMENINO) AL FINAL-------------------------------
        @ExceptionHandler(value = {ListDEException.class})
        public ResponseEntity<Object> handleListDEException(ListDEException ex) {
            try {
                String errorMessage = "Error al tener perro a al inicio" + ex.getMessage();
                return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception exception) {
                String errorMessage = "Error al tener perr@s al final porque no se encuentra agregado " + exception.getMessage();
                return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        private ResponseEntity<Object> handleListDEException(String errorMessage, Exception ex) {
            return new ResponseEntity<>(errorMessage + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //-----------------CONTROLLER CON GETMAPPING-----------------------------
        @GetMapping(path = "/orderpetstostart")
        public ResponseEntity<ResponseDTO> getOrderPetsToStart()  {
            try {
                if (listDEService.getPets() != null) {
                    listDEService.getPets().getOrderPetsToStart();
                    return new ResponseEntity<>(new ResponseDTO(200,
                            "Se ha organizado la lista con las mascotas masculinas  al comienzo con exito ",
                            null), HttpStatus.OK);

                } else {
                    return new ResponseEntity<>(new ResponseDTO(409,
                            "No se puede realizar la acción tenga cuidado", null), HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(500,
                        "Error interno del servidor ", null), HttpStatus.INTERNAL_SERVER_ERROR);
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
        @GetMapping(path = "/getalternatepets")
        public ResponseEntity<ResponseDTO> getAlternatePets() {
            try {
                listDEService.getPets().getAlternatePets();
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Error al alternar las mascotas tenga cuidado ", null),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La lista se alternó exitosamente", null), HttpStatus.OK);
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
        @GetMapping(path = "/deletepetbyage/{age}")
        public ResponseEntity<ResponseDTO> removePetByAge(@Min(value = 1, message = "La edad debe ser mayor que cero") Byte age)  {
            try {
                listDEService.getPets().removePetByAge(age);
                return new ResponseEntity<>(new ResponseDTO(
                        200, "La mascota fue eliminada con exito", null), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Hay un error al eliminar la mascota estar atento ", null), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<ResponseDTO> getAveragePetAge()  {
        try {
            float averageAge = listDEService.getPets().getAveragePetAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La edad promedio de las mascotas que ingresaste son : " + averageAge, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al calcular la edad promedio de las mascotas", null), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping(path = "/petsbylocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode() {
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            for (Location location : locationService.getLocations()) {
                int count = listDEService.getPets().getCountPetsByLocationCode(location.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(location, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS PERROS HAY DE CADA CIUDAD CODIGO B-----------------
    @ExceptionHandler(ListDEException.class)
    public ResponseEntity<String> handle(ListDEException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede identificar la cantidad de mascota/s " + ex.getMessage());
    }


    //--------------------CON GETMAPING------------------------------------------
    @GetMapping(path = "/petsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportPetsByLocationGendersByAge(@Min(value = 0, message = "La edad debe ser mayor o igual a cero") byte age, @NotNull ReportKidsLocationGenderDTO report) {
        try {
            ReportPetsLocationGenderDTO reports =
                    new ReportPetsLocationGenderDTO(locationService.getLocationsByCodeSize(age));
            listDEService.getPets().getReportPetsByLocationGendersByAge(age,report);
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
        } catch (IndexOutOfBoundsException exception2) {
            return new ResponseEntity<>("Se ha producido un error al manejar la excepción de índice fuera de rango"+exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //----------------------------CONTROLLER CON GETMAPPING-----------------------
    @GetMapping(path = "/winpositionpet/{id}/{position}")
    public ResponseEntity<ResponseDTO> winPositionPet(String id, int position){
        try {
            listDEService.getPets().winPositionPet(id,position);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota gano las posiciones en la lista especificadas", null)
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error en ganar posiciones de la mascota en la lista",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN PERRO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS-----------
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {
        try {
            return new ResponseEntity<>("No se ha añadido el perro ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException pointerException) {
            return new ResponseEntity<>("Tiene un error al pedir a la mascota/s perder el numero de posciciones", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------CODIGO CON POSTMAPPING----------------------------
    @GetMapping(path = "/losepositionpet/{id}/{positionpet}")
    public ResponseEntity<ResponseDTO>losePositionPet(String id, int positionpet){
        try {
            listDEService.getPets().losePositionPet(id,positionpet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota perdio posiciones en la lista", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se obtuvo un error al perder la mascota en la lista", null)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------
    @ExceptionHandler(value = {ListDEException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(Exception exception) {
        try {
            return new ResponseEntity<>("No se a tenido en cuenta el rango de edad de las mascota/s", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalStateException stateException) {
            return new ResponseEntity<>("No se a visto la mascota/s añadido ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //----------CONTROLLER CON GETMAPPING------------------------------

    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> getPetRangeByAge()  {
        try {
            List<RangePetDTO> listPetRange = new ArrayList<>();
            for (Ranges i : rangePetService.getRanges()) {
                int quantity = listDEService.getPets().getRangePetByAge(i.getFrom(), i.getTo());
                listPetRange.add(new RangePetDTO(i, quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el rango de los niños es: " + listPetRange,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener el rango de edades",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL
    // FINAL DE LA LISTA A LOS PERROS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        try {
            return new ResponseEntity<>("No se encuentra la mascota/s añadido ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalStateException illegalStateException) {
            return new ResponseEntity<>("No puedo enviar a ningun/a mascota/s de la lista,No se encuentra ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //----------------------CODIGO GETMAPPING------------------------------
    @GetMapping(path = "/sendpetfinish/{letter}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char letter) {
        try {
            listDEService.getPets().sendPetToTheEndByLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "los niños con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------------EJERCICIO DIA 8/05/23--------------------------------
    @GetMapping(path = "/removeNodeByIdentificationPet(identification)")
    public ResponseEntity<ResponseDTO> removeNodeByIdentificationPet(@NotNull String identification) {
        try {
            listDEService.getPets().removeNodeByIdentificationPet(identification);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se removió al niño por identificación", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


}





















