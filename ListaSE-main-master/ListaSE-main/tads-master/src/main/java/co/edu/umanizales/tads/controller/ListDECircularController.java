package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEEExceptionCircular;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListCircularService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/circular")
public class ListDECircularController {
    @Autowired
    private ListCircularService listCircularService;

    @GetMapping(path = "/getinto/{x}")
    public ResponseEntity<ResponseDTO> getinto(Pet pet,int x) {
        try {
            listCircularService.getPetsde().getinto(pet,x);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se añadio al perro",null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(400,
                "no se añadio el perro tiene problemas ",null),HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/showlist")
    public ResponseEntity<ResponseDTO>showList(){
        try {
            listCircularService.getPetsde().showList();
        }catch (ListDEEExceptionCircular listDEEExceptionCircular){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "ya se ve al perro añadido",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se añadio el perro añadido revise ",null),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(path = "insertaatposition")
    public ResponseEntity<ResponseDTO>insertAtPosition(int position, int data, Pet pet){
        try {
            listCircularService.getPetsde().insertAtPosition(position,data,pet);
        }catch (ListDEEExceptionCircular listDEEExceptionCircular){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se incerto de posicion buena suerte",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "el perro no se incerto de poscion ",null),HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/addfirst")
    public ResponseEntity<ResponseDTO>addFirst(int data, Pet pet){
        try {
            listCircularService.getPetsde().addFirst(data, pet);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se añadio al principio el perro",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se añadio al inicio el perro ",null),HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/addlast")
    public ResponseEntity<ResponseDTO>addFinal(int data,Pet pet){
        try {
            listCircularService.getPetsde().addFinal(data, pet);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se añadio a lo ultimo ",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se añade a lo ultimo",null),HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/addleftdog")
    public ResponseEntity<ResponseDTO>addLeftDog(int data, Pet pet){
        try {
            listCircularService.getPetsde().addLeftDog(data, pet);
        }catch (ListDEEExceptionCircular listDEEExceptionCircular){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se baño el perro en el lado izquierdo",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se baño en el lado izquierdo",null),HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/addrightdog")
    public ResponseEntity<ResponseDTO>addRightDog(int data, Pet pet){
        try {
            listCircularService.getPetsde().addRightDog(data, pet);
        }catch (ListDEEExceptionCircular listDEEExceptionCircular){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se baño el perro en el lado derecho ",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se baño en el lado izquierdo el perro",null),HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/addrightleftdog")
    public ResponseEntity<ResponseDTO>addRightLeftdog(int data, boolean atBeginning, Pet pet){
        try {
            listCircularService.getPetsde().addRightLeftdog(data,atBeginning,pet);
        }catch (ListDEEExceptionCircular listDEEExceptionCircular){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se baño el perro tanto en la izquierda como en la derecha",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se baño el perro en la parte izquierda como en la derecha",null),HttpStatus.NOT_FOUND);
    }
}
