package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import co.edu.umanizales.tads.exception.ListSEException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import java.util.List;


@RestController
@RequestMapping(path = "/lists")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;



    //En este codigo vamos a ver


//En esta parte del codigo vemos que el getMaping sirve para enviar el HTTP o la url hacer por


    //todos los 12 metodos




    //----------------OBTENER NIÑOS--------------------------------
    @GetMapping(path = "getkids")
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids().getHead(), null), HttpStatus.OK);
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
        listSEService.deleteKidByAge((byte) 12);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se elimino el niño por edad", null), HttpStatus.OK);

    }




    //Esta parte lo que hace es ganar posiciciones
    @GetMapping(path = "/earn_positions")
    public ResponseEntity<ResponseDTO> earnPositions() {
        listSEService.earnPositions("123", 21);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Posiciones de los niños actualizados", null), HttpStatus.OK);
    }

    //Esta parte lo que hace es borrar edad por id
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
    @PostMapping(path="addkid")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) throws ListSEException {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getAge(), kidDTO.getGender(),
                            location, kidDTO.getCodeLocation()));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el petacón",
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
        listSEService.getKids().getOrderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha intercambiado" +
                " los niños al inicio y niñas al final ", null), HttpStatus.OK);

    }

    //-------------ESTE ES EL CONTROLLER DE INTERCALAR NIÑO,NIÑA,NIÑO (3)-------------------------
    @GetMapping(path = "/alternatekids")
    public ResponseEntity<ResponseDTO> getAlternateKids() throws ListSEException {
        listSEService.getKids().getAlternateKids();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha alternado la lista",
                null), HttpStatus.OK);
    }


    //--------------ESTE ES EL CONTROLER DE DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA (4)-----------

    @GetMapping(path = "/removekidbyage/{age}")
    public ResponseEntity<ResponseDTO> removeKidByAge(@PathVariable byte age) throws ListSEException {
        listSEService.getKids().removeKidByAge((byte) 12);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se ha removido los niños por edad",null),HttpStatus.OK);
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

    @GetMapping(path = "/get_count_kids_by_location_code")
    public ResponseEntity<ResponseDTO> getCountKidsByLocationCode() throws ListSEException {
        listSEService.getKids().getCountKidsByLocationCode("1");
        return new ResponseEntity<>(new ResponseDTO(200,
                "ya esta identificado por ciudad el niño", null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report =
                new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids()
                .getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
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
    @GetMapping(path = "/winPositionKid/{id}/{win}")
    public ResponseEntity<ResponseDTO> winPositionKid(@PathVariable String id, @PathVariable int win) throws ListSEException {
        try {
            listSEService.winPositionKid(id, win);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "El niño ha ganado " + win + " posición(es)", null), HttpStatus.OK);
        } catch (Exception e) {
            throw new ListSEException("Ha ocurrido un error en el servidor: " + e.getMessage());
        }
    }


    //-----------------ESTE ES EL CONTROLLER DE:METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS (8) -------------

    @PostMapping(path = "/kids/addkidatposforlose/{pos}")
    public ResponseEntity<ResponseDTO> addKidAtPosForLose(@RequestBody Kid kid, @PathVariable int pos) {
        listSEService.addKidAtPosForLose(kid, pos);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Niño agregado exitosamente en la posición " + pos,null), HttpStatus.OK);
    }



    //----------------ESTE ES EL CONTROLLER DE OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES(9)--------------

    @GetMapping(path = "/reportbyage/{age}")
    public ResponseEntity<ResponseDTO> reportByAge() throws ListSEException {
        listSEService.getKids().reportByAge((byte) 2,(byte) 4);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Este es el reporte de niños",null),HttpStatus.OK);

    }



    //------ESTE ES EL CONTROLLER DE IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS
//----------QUE SU NOMBRE INICIE POR UNA LETRA DADA(10)---------------------

    @GetMapping(path = "/addtostartnamechar/{id}")
    public ResponseEntity<ResponseDTO> addToStartNameChar(@PathVariable String id, String name) {
        listSEService.addToStartNameChar(id, name);
        return new ResponseEntity<>(new ResponseDTO(200,
                "el nombre fue agregado al principio de la lista", null), HttpStatus.OK);
    }

}





















