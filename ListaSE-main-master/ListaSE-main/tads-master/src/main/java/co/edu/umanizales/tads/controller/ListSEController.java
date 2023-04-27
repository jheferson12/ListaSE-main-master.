package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Node;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import co.edu.umanizales.tads.exception.ListSEException;
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
    //----------------OBTENER NIÑOS--------------------------------
    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    //Esta parte es la cantidad de niños en la ciudad
    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() throws ListSEException {
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


//Esta parte cambia extremos

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "SE han intercambiado los extremos",
                null), HttpStatus.OK);
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
        listSEService.getKids().deleteByIdentifications(id);
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

    //------------------CODIGO PARA AÑADIR AL NIÑO-------------------------------------------
    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) throws ListSEException {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404,"La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getAge(), kidDTO.getGender(),
                            location,kidDTO.getCodeLocation()));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el petacón",
                null), HttpStatus.OK);

    }



    //---------------------------------DESDE AQUI COMIENZA LOS CODIGOS DEL 1 AL 10------------------------
    //-----------ESTE ES EL CONTROLER DE INVETIR LISTA(1)---------------------------
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> getInvert() {
        listSEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    //--------------ESTE ES EL CONTROLER DE NIÑOS AL PRINCIPIO Y NIÑAS AL FINAL(2)----------------
    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> getOrderBoysToStart() throws ListSEException {
        listSEService.getKids().getorderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha intercambiado" +
                " los niños al inicio y niñas al final ", null), HttpStatus.OK);

    }

    //-------------ESTE ES EL CONTROLLER DE INTERCALAR NIÑO,NIÑA,NIÑO (3)-------------------------
    @GetMapping(path = "/alternatekids")
    public ResponseEntity<ResponseDTO> getAlternateKids() throws ListSEException {
        ResponseDTO responseDTO = new ResponseDTO(200,
                "Se ha obtenido la lista alternada de niños y niñas ",
                listSEService.getKids().getAlternateKids());
        return ResponseEntity.ok(responseDTO);


    }
    //--------------ESTE ES EL CONTROLER DE DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA (4)-----------

    @GetMapping(path = "/removekidbyage/{age}")
    public ResponseEntity<ResponseDTO> removeKidByAge(@PathVariable byte age) throws ListSEException {

        boolean removed = listSEService.getKids().removeKidByAge(age);
        if (removed) {
            return ResponseEntity.ok(new ResponseDTO(200, "Se ha eliminado al niño o niña por la edad " + age, null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(400,
                    "no se encontro un niño o niña con la edad " + age, null));

        }
    }
    //-----------------ESTE ES EL CONTROLER DE OBTENER EL PROMEDIO DE EDAD DE LOS NIÑOS DE LA LISTA (5)---------------

    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> getAverageAge() {
        List<Kid> kids = listSEService.getKids().getKids();
        if (kids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "No se encontraron niños", null));
        }
        double sum = 0;
        for (Kid kid : kids) {
            sum += kid.getAge();
        }
        double averageAge = sum / kids.size();
        return ResponseEntity.ok(new ResponseDTO(200, "La edad promedio de los niños es " + averageAge, null));
    }
    //-------------------ESTE ES EL CONTROLLER DE GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD (6)----------

    @GetMapping(path = "/getcount_kids_by_location_code")
    public ResponseEntity<ResponseDTO> getCountKidsByLocationCode() throws ListSEException {
        listSEService.getKids().getCountKidsByLocationCode("1");
        return new ResponseEntity<>(new ResponseDTO(200,
                "ya esta identificado por ciudad el niño", null), HttpStatus.OK);
    }



    /*@GetMapping(path = "/count_kid_by_city")
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

     */

    //----------------ESTE ES EL CONTROLLER DE: METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE ADELANTE
    //-----------UN NUMERO DE POSICIONES DADAS(7)----------------------------------------------



    @GetMapping(path = "/moveforward")
    public ResponseEntity<ResponseDTO> moveForward() {
        ListSE<Kid> kids = listSEService.getKids();
        Kid nextKid = kids.moveForward();

        if (nextKid != null) {
            return ResponseEntity.ok(new ResponseDTO(200,
                    "Se ha movido a la siguiente posición en la lista de niños", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404,
                "No hay más niños en la lista", null));
    }


    //-----------------ESTE ES EL CONTROLLER DE:METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS (8) -------------

    @GetMapping(path = "/kids/{id}")
    public ResponseEntity<ResponseDTO> getKidById(@PathVariable Long id) {
        Kid kid = null;
        for (Kid k : kidsList) {
            if (k.getId().equals(id)) {
                kid = k;
                break;
            }
        }
        if (kid != null) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Niño encontrado exitosamente", kid), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(404,
                    "No se encontró el niño", null), HttpStatus.NOT_FOUND);
        }
    }



    //----------------ESTE ES EL CONTROLLER DE OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES(9)--------------
    @GetMapping(path = "/reportbyage")
    public ResponseEntity<ResponseDTO> reportByAge() {
        ListSE<Kid> kids = listSEService.getKids();
        Map<Byte, Integer> ageCountMap = new HashMap<>();

        for (Kid kid : kids) {
            byte age = kid.getAge();
            if (ageCountMap.containsKey(age)) {
                ageCountMap.put(age, ageCountMap.get(age) + 1);
            } else {
                ageCountMap.put(age, 1);
            }
        }

        List<AgeCountDTO> ageCountList = new ArrayList<>();
        for (Byte age : ageCountMap.keySet()) {
            ageCountList.add(new AgeCountDTO(age, ageCountMap.get(age)));
        }

        return ResponseEntity.ok(new ResponseDTO(200, "Report generated successfully", ageCountList));
    }


    //------ESTE ES EL CONTROLLER DE IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS
//----------QUE SU NOMBRE INICIE POR UNA LETRA DADA---------------------

    @GetMapping(path = "/moveChildren")
    public ResponseEntity<ResponseDTO> moveChildren() {
        ListSE<Kid> kids = listSEService.getKids();
        boolean moved =;
        if (moved) {
            return ResponseEntity.ok(new ResponseDTO(200, "Todos los niños se han movido hacia adelante en la lista", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "No hay niños en la lista", null));
        }
    }

}




















