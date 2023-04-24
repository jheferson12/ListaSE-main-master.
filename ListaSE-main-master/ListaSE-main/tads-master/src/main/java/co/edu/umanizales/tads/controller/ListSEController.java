package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Node;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/lists")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;
    List<Kid> listKid1 = new ArrayList<>();


    //En este codigo vamos a ver


//En esta parte del codigo vemos que el getMaping sirve para enviar el HTTP o la url hacer por


    //todos los 12 metodos
    private ListSEService listKid;
    private Node head;

    // Esta parte es el promedio de edades
    @GetMapping(path = "/average-age")
    public ResponseEntity<ResponseDTO> avergeAge() {
        return new ResponseEntity<>(new ResponseDTO(200,
                "el promedio concluido fue: " + listSEService.getAverageAge(), null), HttpStatus.OK);

    }

    //Esta parte es la cantidad de niños en la ciudad
    @GetMapping(path = "/getcount_Kids_by_LocationCode")
    public ResponseEntity<ResponseDTO> getCountKidsByLocationCode() {
        listSEService.getKids().getCountKidsByLocationCode("123456");
        return new ResponseEntity<>(new ResponseDTO(200,
                "ya esta identificado por ciudad el niño", null), HttpStatus.OK);
    }


//Esta parte cambia extremos

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    //Esta parte pierde posiciones
    @GetMapping(path = "/losepositions/{id}")
    public ResponseEntity<ResponseDTO> losePositions(@PathVariable String id, int lose) {
        listSEService.losePositions(id, lose);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Niño agregado en posicion decidida", null), HttpStatus.OK);
    }

    //Esta parte borra por edad
    @GetMapping(path = "/deletebyAge")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) {
        listSEService.deleteKidByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se elimino el niño por edad", null), HttpStatus.OK);

    }

    //Esta parte es agregar al principio de la lista
    @GetMapping(path = "/addtostartnamechar/{id}")
    public ResponseEntity<ResponseDTO> addToStartNameChar(@PathVariable String id, String name) {
        listSEService.addToStartNameChar(id, name);
        return new ResponseEntity<>(new ResponseDTO(200,
                "el nombre fue agregado al principio de la lista", null), HttpStatus.OK);
    }

    //Esta parte lo que hace es ganar posiciciones
    @GetMapping(path = "/earn_positions")
    public ResponseEntity<ResponseDTO> earnPositions() {
        listSEService.earnPositions("123", 21);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Posiciones de los niños actualizados", null), HttpStatus.OK);
    }

    //Esta parte lo que hace es borrar edad por el id
    @GetMapping(path = "/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteByIdentifications(@PathVariable String id) {
        listSEService.getKids().deleteByIdentification(id);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Niño eliminado correctamente", null), HttpStatus.OK);
    }

    //Esta es de generar rangos por edades
    @GetMapping(path = "/age_range_report")
    public ResponseEntity<ResponseDTO> generateAgeRangeReport() {
        locationService.getLocationsByCode("1697867");
        return new ResponseEntity<>(new ResponseDTO(200,
                "Age range report generated", null), HttpStatus.OK);
    }

    @PostMapping
    /*public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.CONFLICT);
        }
        listSEService.getKids().add(new Kid(kidDTO.getIdentification(),kidDTO.getName(),
                kidDTO.getAge(), kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el petacón",
                null), HttpStatus.OK);
    }

     */

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            } else if (count == 0) {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "no existen niños con este codigo de localizacion  ", null),
                        HttpStatus.BAD_REQUEST);


            } else {
                return new ResponseEntity<>(new ResponseDTO(
                        200, kidsByLocationDTOList, null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,
                kidsByLocationDTOList, null), HttpStatus.OK);
    }

    //invertir lista
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        listSEService.getKids().invert();
        return new ResponseEntity<>(new ResponseDTO(
                200, "SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/count_kid_by_city")
    public Map<String, Integer> countKidByCity() {
        Map<String, Integer> KidByCity = new HashMap<>();

        Node current = head;
        while (current != null) {
            Kid kid = current.getData();
            Location city = kid.getLocation();
            KidByCity.putIfAbsent(String.valueOf(city), 0);
            KidByCity.put(String.valueOf(city),KidByCity.get(city)+1);
            current=current.getNext();
        }
        return KidByCity;


    }
}
















