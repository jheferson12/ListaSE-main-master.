package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

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
        @ExceptionHandler(value = {ListDEException.class})
        public ResponseEntity<Object> handleListDEException(ListDEException ex, WebRequest request) {
            String errorMessage = "Error al invertir la lista: " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //-----------------------------CONTROLLER 2 NIÑOS AL INCIO Y NIÑAS AL FINAL-------------------------------
        @ExceptionHandler(value = {ListDEException.class})
        public ResponseEntity<Object> handleListDEException(ListDEException ex) {
            return handleException("Error en ordenar los perros al comienzo", ex);
        }

        private ResponseEntity<Object> handleException(String errorMessage, Exception ex) {
            // Aquí puedes manejar la excepción de cualquier forma que necesites
            // y devolver una respuesta personalizada al cliente
            return new ResponseEntity<>(errorMessage + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //----------------------CODIGO 3 INTERCALAR PETM-PETF-PETM-PETF-------------------------------

        @ExceptionHandler(value = {NoSuchElementException.class})
        public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
            return new ResponseEntity<>("No se encontró el elemento solicitado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        //-------------------------CODIGO 4 DADA UNA EDAD ELIMINAR A LOS PERROS DE LA EDAD DADA -----------------
        @ExceptionHandler(value = {IllegalStateException.class})
        public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
            return new ResponseEntity<>("Ocurrió un estado ilegal: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //---------CODIGO 5 OBTENER EL PROMEDIO DE EDAD DE LOS PERROS DE LA LISTA -------------------
        @ExceptionHandler(value = {FileNotFoundException.class})
        public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
            return new ResponseEntity<>("El archivo no fue encontrado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        //-----------CODIGO 6 GENERAR UN REPORTE QUE ME DIGA CUANTOS PERROS HAY DE CADA CIUDAD-----------------
        @ExceptionHandler(value = ListDEException.class)
        public ResponseEntity<Object> handleListSEException(ListDEException ex) {
            String errorMessage = "Error al generar un reporte de perros por ciudad " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //------------CODIGO 7 METODO QUE ME PERMITA DECIRLE A UN PERRO DETERMINADO QUE ADELANTE  UN NUMERO DE POSICIONES DADAS---------

        @ExceptionHandler(value = {IndexOutOfBoundsException.class})
        public ResponseEntity<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
            return new ResponseEntity<>("Se ha producido una excepción de índice fuera de rango", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //-----------------CODIGO 8 METODO QUE ME PERIMITA DECIRLE A UN PERRO DETERMINADO QUE PIERDA UN NUMERO DE POSICIONES DADAS-----------
        @ExceptionHandler(value = {NullPointerException.class})
        public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
            String errorMessage = "Error de referencia nula: " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //-------------------CODIGO 9 OBTENER UN INFORME DE PERROS POR RANGO DE EDADES--------------------
        @ExceptionHandler(value = {ListDEException.class, IllegalArgumentException.class})
        public ResponseEntity<Object> handleException(Exception ex) {
            String errorMessage = "Ocurrió un error: " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //------------CODIGO 10 IMPLEMENTAR UN METODO QUE ME PERMITA ENVIAR AL
        // FINAL DE LA LISTA A LOS PERROS QUE SU NOMBRE INICIE  CON UNA LETRA DADA -----
        @ExceptionHandler(value = IllegalArgumentException.class)
        public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
            String errorMessage = "Error al enviar al final de la lista los perros : " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }

}

















