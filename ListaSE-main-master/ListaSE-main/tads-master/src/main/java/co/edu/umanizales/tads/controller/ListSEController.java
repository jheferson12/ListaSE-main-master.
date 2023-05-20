package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeKidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import co.edu.umanizales.tads.exception.ListSEException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/lists")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangeKidService rangeKidService;


    //En este codigo vamos a ver
//En esta parte del codigo vemos que el getMaping sirve para enviar el HTTP o la url hacer por


    //todos los 12 metodos




    //Esta parte es la cantidad de niños en la ciudad
    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        try {
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
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "No hay niños que podamos ingresar las localizaciones de los niños" + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//Esta parte cambia extremos

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO>changeExtremes(){
        try {
            listSEService.getKids().changeExtremes();
        }catch (ListSEException listSEException){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "ya se cambio los extremos correctamente ",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se cambio los extremos",null),HttpStatus.NOT_FOUND);
    }


    //Esta parte borra por edad






    //Esta parte lo que hace es borrar edad por id
    @GetMapping(path = "/deletebyid/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable String id)  {
        try {
            listSEService.getKids().deleteById(id);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "los niños ya fueron eliminados por su identificacion :)", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Tiene un error al eliminar los niños con la identificacion :(", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Esta es de generar rangos por edades
    @GetMapping(path = "/age_range_report")
    public ResponseEntity<ResponseDTO> generateAgeRangeReport() {
        locationService.getLocationsByCode("1697867");
        return new ResponseEntity<>(new ResponseDTO(200,
                "Age range report generated", null), HttpStatus.OK);
    }

    //------------------CODIGO PARA AÑADIR AL NIÑO-------------------------------------------
    @PostMapping(path = "/addkid")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404,"La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el petacón",
                null), HttpStatus.OK);

    }
    //----------------OBTENER NIÑOS--------------------------------
    @GetMapping(path = "getkids")
    public ResponseEntity<ResponseDTO> getKids() {
        try {
            return new ResponseEntity<>(new ResponseDTO(
                    200, listSEService.getKids().print(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de mascotas revise" + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //---------------------------------DESDE AQUI COMIENZA LOS CODIGOS DEL 1 AL 10------------------------
    //-----------ESTE ES EL CONTROLER DE INVETIR LISTA(1)---------------------------
    @GetMapping("/invertkid")
    public ResponseEntity<ResponseDTO> invertKid()  {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().invertKid();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "La lista de los niños se invertio correctamente", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede invertir a el niño tenga cuidado", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error por el servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------ESTE ES EL CONTROLER DE NIÑOS AL PRINCIPIO Y NIÑAS AL FINAL(2)----------------
    @GetMapping(path = "/orderkidstostart")
    public ResponseEntity<ResponseDTO>ordersKidToStart()  {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().ordersKidToStart();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha organizado la lista con los niños al comienzo con exito ",
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


    //-------------ESTE ES EL CONTROLLER DE INTERCALAR NIÑO,NIÑA,NIÑO (3)-------------------------
    @GetMapping(path = "/getalternatekids")
    public ResponseEntity<ResponseDTO> alternateKids() {
        try {
            listSEService.getKids().alternateKids();
            return new ResponseEntity<>(new ResponseDTO(200,
                    "la lista se alterno exitosamente",null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al alternar las mascotas tenga cuidado " +e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //--------------ESTE ES EL CONTROLER DE DADA UNA EDAD ELIMINAR A LOS NIÑOS DE LA EDAD DADA (4)-----------

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age)  {
        try {
            listSEService.getKids().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El niño fue eliminada con exito", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //------------------------------------CONTROLLER PARA AÑADIR EL POSICION---------------
    /*@PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addKidsByPosition(KidDTO kidDTO,  int position) {
        try {
            Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
            }
            Kid kid = new Kid(kidDTO.getIdentification(),kidDTO.getName(),kidDTO.getAge(),kidDTO.getGender(), location);
            listSEService.getKids().addKidsByPosition(kid, position);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño fue añadida en la posición solicitada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     */

    //-----------------ESTE ES EL CONTROLER DE OBTENER EL PROMEDIO DE EDAD DE LOS NIÑOS DE LA LISTA (5)---------------

    @GetMapping(path = "/averageagekid")
    public ResponseEntity<ResponseDTO> getAverageByAge()  {
        try {
            float averageAge = listSEService.getKids().getAverageByAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La edad promedio de los niños o niñas que ingresaste son : " + averageAge, null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500,"Se produjo un error al calcular la edad promedio de los niños o las niñas", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------ESTE ES EL CONTROLLER DE GENERAR UN REPORTE QUE ME DIGA CUANTOS NIÑOS HAY DE CADA CIUDAD (6)----------

    @GetMapping(path = "/kidsbylocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        try {
            for (Location loc : locationService.getLocations()) {
                int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, kidsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/kidsbylocationdepartament")
    public ResponseEntity<ResponseDTO> getCountKidsByDeptCode() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        try {
            for (Location loc : locationService.getLocations()) {
                int count = listSEService.getKids().getCountKidsByDeptCode(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, kidsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //-----------------AÑADIR POSICION----------------------------------
    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody KidDTO kidDTO, @PathVariable int position) {
        try {
            Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
            }
            Kid kid = new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(), kidDTO.getGender(), location);
            listSEService.getKids().addInPosition(position, kid);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño fue añadido en la posición solicitada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operación", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //----------------ESTE ES EL CONTROLLER DE: METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE ADELANTE
    //-----------UN NUMERO DE POSICIONES DADAS(7)----------------------------------------------

    @GetMapping(path = "/win_position/{id}/{position}")
    public ResponseEntity<ResponseDTO> winPositionKid( @PathVariable String id , @PathVariable  int positon, ListSE listSE) {
        try {
            listSEService.getKids().winPositionKid(id,positon,listSE);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "El niño ganó la posición propuesta", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    "Ingreso un dato inecesario intentalo de nuevo "+e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    //-----------------ESTE ES EL CONTROLLER DE:METODO QUE ME PERMITA DECIRLE A UN NIÑO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS (8) -------------

    @GetMapping(path = "/losepetposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> loseKidPosition(@PathVariable String id, @PathVariable Integer position) {
        try {
            listSEService.getKids().loseKidPosition(id, position);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota perdio posiciones en la lista felicidades", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al retroceder la mascota en la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //----------------ESTE ES EL CONTROLLER DE OBTENER UN INFORME DE NIÑOS POR RANGO DE EDADES(9)--------------

    @GetMapping(path = "/rangebyage")
    public ResponseEntity<ResponseDTO> getRangeByAge()  {
        try {
            List<RangeDTOKids> kidsRangeList = new ArrayList<>();

            if (listSEService.getKids() != null) {
                for (Ranges i : rangeKidService.getRangesList()) {
                    int quantity = listSEService.getKids().getRangeByAge(i.getFrom(), i.getTo());
                    kidsRangeList.add(new RangeDTOKids(i, quantity));
                }
                return new ResponseEntity<>(new ResponseDTO(200,  kidsRangeList, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(404, "No se puede realizar la acción", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //------ESTE ES EL CONTROLLER DE IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL FINAL DE LA LISTA A LOS NIÑOS
//----------QUE SU NOMBRE INICIE POR UNA LETRA DADA(10)---------------------

    @GetMapping(path = "/kidsfirstletter/{letter}")
    public ResponseEntity<ResponseDTO> getKidByFirstLetter(@PathVariable char letter) {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().getKidByFirstLetter(letter);
                return ResponseEntity.ok().body(new ResponseDTO(200,
                        "Se han enviado los niños con la letra dada al final", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(404, "No se puede realizar la acción", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Error interno del servidor", null));
        }
    }
}





















