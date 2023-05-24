package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.PetsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ReportPetsLocationBathedPetsDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEEExceptionCircular;
import co.edu.umanizales.tads.model.ListDECircular;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListCircularService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/circular")
public class ListDECircularController {
    @Autowired
    private ListCircularService listCircularService;
    @Autowired
    private LocationService locationService;

    @PostMapping(path = "/addpet")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationsByCode(petDTO.getCodeLocationPet());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            listCircularService.getPetsde().add(new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getBreed(),
                    petDTO.getGender(), location, petDTO.getId(), false));
        } catch (ListDEEExceptionCircular e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado la mascota que ingresó",
                null), HttpStatus.OK);
    }
//--------------------VER LOS PERROS O LISTAR---------------------------
    @GetMapping(path = "getpets")
    public ResponseEntity<ResponseDTO> getPets() {
        try {
            return new ResponseEntity<>(new ResponseDTO(
                    200, listCircularService.getPetsde().print(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de mascotas revise" + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//----------------------------INSERTAR EN PSOCICION------------------

    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetDTO petDTO, @PathVariable int position)throws ListDEEExceptionCircular {
        Location location = locationService.getLocationsByCode(petDTO.getCodeLocationPet());
        listCircularService.getPetsde().addByPosition(
                new Pet(petDTO.getName(), petDTO.getAge(),
                        petDTO.getBreed(), petDTO.getGender(),location,
                        petDTO.getId(),false),position);

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada en la posicion: "
                + position, null),
                HttpStatus.OK);
    }

    //-----------------------AÑADIR DE PRIMERAS--------------------------.
    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addFirst(@RequestBody PetDTO petDTO)throws ListDEEExceptionCircular {
        Location location = locationService.getLocationsByCode(petDTO.getCodeLocationPet());
        listCircularService.getPetsde().addFirst(
                new Pet(petDTO.getName(), petDTO.getAge(),
                        petDTO.getBreed(), petDTO.getGender(),
                        location, petDTO.getId(),false));

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al inicio", null),
                HttpStatus.OK);
    }
    //-----------------------AÑADIR AL FINAL---------------------
    @PostMapping(path = "/addfinal")
    public ResponseEntity<ResponseDTO> addFinal(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationsByCode(petDTO.getCodeLocationPet());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }

        try {
            listCircularService.getPetsde().addFinal(new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getBreed(),
                    petDTO.getGender(), location, petDTO.getId(), false));
        } catch (ListDEEExceptionCircular e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado la mascota que ingresó al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/takeshower/{letter}")
    public ResponseEntity<ResponseDTO> takeShower(@PathVariable char letter) {
        char letterLower = Character.toLowerCase(letter);
        Pet pet = listCircularService.getPetsde().takeShower(letter);

        if (pet == null) {
            return new ResponseEntity<>(
                    new ResponseDTO(409, "No hay perros para bañar", null),
                    HttpStatus.BAD_REQUEST);
        } else {
            String message;
            if (pet == listCircularService.getPetsde().getFirst()) {
                message = "Se bañó la primera mascota ingresada";
            } else {
                String direction = (letterLower == 'd') ? "derecha" : "izquierda";
                int number = listCircularService.getPetsde().getPetNumber(pet);
                message = "Se bañó la mascota número " + number + " dirigiéndose a la " + direction;
            }
            return new ResponseEntity<>(
                    new ResponseDTO(200, message, null),
                    HttpStatus.OK);
        }
    }
    //----------------------ME MUESTRA EL TAMAÑO-------------------------------
    @GetMapping(path = "/tamaño")
    public ResponseEntity<ResponseDTO>tamaño(){

        int j=listCircularService.getPetsde().tamaño();
        return new ResponseEntity<>(new ResponseDTO(200
                ,j,null),HttpStatus.OK);
    }
    @GetMapping(path = "/petsbylocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode() {
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try {
            // Obtener la lista doblemente enlazada circular de mascotas
            ListDECircular petsList = listCircularService.getPetsde();

            if (petsList != null) {
                // Obtener la lista de ubicaciones
                List<Location> locations = locationService.getLocations();

                for (Location loc : locations) {
                    int count = petsList.getCountPetsByLocationCode(loc.getCode());
                    if (count > 0) {
                        petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
                    }
                }
            }

            return new ResponseEntity<>(new ResponseDTO(200, petsByLocationDTOList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/reportspets")
    public ResponseEntity<ResponseDTO> getReportsPetsLocationByBathedPets()throws ListDEEExceptionCircular {
            ReportPetsLocationBathedPetsDTO report = new ReportPetsLocationBathedPetsDTO(
                    locationService.getLocationsByCodeSize(8));
            listCircularService.getPetsde().getReportPetsByLocationByBathedPets(report);
            return ResponseEntity.ok(new ResponseDTO(200, report, null));

    }
}
