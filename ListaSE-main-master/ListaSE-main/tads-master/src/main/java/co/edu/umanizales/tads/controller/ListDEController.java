package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.*;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangePetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/lists2")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangePetService rangePetService;
//------------------OBTENER NIÑOS------------------------
    @GetMapping(path = "getpets")
    public ResponseEntity<ResponseDTO> getPets() {
        try {
            return new ResponseEntity<>(new ResponseDTO(
                    200, listDEService.getPets().print(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de mascotas revise" + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        //----------------AÑADIR MASCOTA------------------
        @PostMapping(path = "/addpet")
        public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO)  {
            Location location = locationService.getLocationsByCode(petDTO.getCodeLocationPet());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe",
                        null), HttpStatus.OK);
            }
            try {
                listDEService.getPets().add(new Pet(petDTO.getName(),petDTO.getAge(),petDTO.getBreed(),petDTO.getGender(),location,petDTO.getId(),false));

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
        @PostMapping(path = "/addbyposition/{position}")
        public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetDTO petDTO, @PathVariable int position) {
            try {
                Location location = locationService.getLocationsByCode(petDTO.getCodeLocationPet());
                if (location == null) {
                    return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
                }
                Pet pet = new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getBreed(), petDTO.getGender(), location, petDTO.getId(), false);
                listDEService.getPets().addByPosition(pet, position);
                return new ResponseEntity<>(new ResponseDTO(200, "La mascota fue añadida en la posición: "+position, null), HttpStatus.OK);
            } catch (ListDEException e) {
                return new ResponseEntity<>(new ResponseDTO(400, "Error al añadir la mascota: " + e.getMessage(), null), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



        //----------------CON CONTROLLER GETMAPPING-----------
        //---------------------------------CONTROLLER 1 INVERTIR LA LISTA------------------------------------
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

        //-----------------CONTROLLER (2) ORDENAR LOS PERROS AL INICIO-----------------------------
        @GetMapping(path = "/orderpetstostart")
        public ResponseEntity<ResponseDTO> getOrderPetsToStart() {
            try {
                if (listDEService.getPets() != null) {
                    listDEService.getPets().getOrderPetsToStart();
                    return new ResponseEntity<>(new ResponseDTO(200,
                            "Se ha organizado la lista con las mascotas masculinas al comienzo con exito",
                            null), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponseDTO(409,
                            "No se puede realizar la acción, tenga cuidado", null), HttpStatus.BAD_REQUEST);
                }
            } catch (ListDEException e) {
                return new ResponseEntity<>(new ResponseDTO(400,
                        "Se ha producido un error al ordenar la lista de niños: " + e.getMessage(), null),
                        HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(500,
                        "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //------------------CONTROLLER CON GETMAPPING(3)MEZCLAR--------------------------
        @GetMapping(path = "/getalternatepets")
        public ResponseEntity<ResponseDTO> getAlternatePets() {
            try {
                listDEService.getPets().getAlternatePets();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "la lista se alterno exitosamente",null),HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, "Error al alternar las mascotas tenga cuidado " +e.getMessage(), null),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS PERROS DE LA EDAD DADA -----------------

        @GetMapping(path = "/deletepetbyage/{age}")
        public ResponseEntity<ResponseDTO> removePetByAge(@PathVariable byte age)  {
            try {
                listDEService.getPets().removePetByAge(age);
                return new ResponseEntity<>(new ResponseDTO(
                        200, "La mascota fue eliminada con exito", null), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDTO(
                        500, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

//-----------------VER PROMEDIO(5)---------------------------------
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

    //-------------CONTROLLER 6 VER LAS MASCOTAS POR UBICACION------------------------
    @GetMapping(path = "/petsbylocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode() {
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            for (Location loc : locationService.getLocations()) {
                int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
                if (count > 0) {
                    petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------------CONTROLLER (7) GANAR POSICION------------------------------------------
    @GetMapping(path = "/winpetposition/{code}/{num}")
    public ResponseEntity<ResponseDTO> winPetPosition(@PathVariable String code, @PathVariable int num) {
        try {
            listDEService.getPets().winPetPosition(code, num);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota ganó las posiciones en la lista especificadas", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al ganar posiciones de la mascota en la lista: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------CONTROLLER (8) PERDER POSCION----------------------------
    @GetMapping(path = "/losepetposition/{code}/{num}")
    public ResponseEntity<ResponseDTO> losePetPosition(@PathVariable String code, @PathVariable int num) {
        try {
            listDEService.getPets().losePetPosition(code, num);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota perdió posiciones en la lista, ¡felicidades!", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al retroceder la mascota en la lista", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //----------CONTROLLER 9 RANGO DE EDADES------------------------------

    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> getPetRangeByAge()  {
        try {
            List<RangePetDTO> listPetRange = new ArrayList<>();
            for (Ranges i : rangePetService.getRanges()) {
                int quantity = listDEService.getPets().getRangePetByAge(i.getFrom(), i.getTo());
                listPetRange.add(new RangePetDTO(i, quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, listPetRange,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener el rango de edades",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //----------------------CONTROLLER 10 BUSCAR A LA MASCOTA POR LA LETRA INICIAL------------------------------
    @GetMapping(path = "/pettofinishbyletter/{letter}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char letter) {
        try {
            listDEService.getPets().sendPetToTheEndByLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Los niños con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------------EJERCICIO DIA 8/05/23--------------------------------
    @GetMapping(path = "/removenodebyidentificationpet/{identification}")
    public ResponseEntity<ResponseDTO> removeNodeByIdentificationPet(@PathVariable String identification) {
        try {
            listDEService.getPets().removeNodeByIdentificationPet(identification);
            return ResponseEntity.ok(new ResponseDTO(200, "Se removió el nodo por identificación", null));
        } catch (ListDEException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(400, e.getMessage(), null));
        }
    }








}
