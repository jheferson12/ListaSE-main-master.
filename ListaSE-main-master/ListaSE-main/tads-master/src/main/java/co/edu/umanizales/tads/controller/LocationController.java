package co.edu.umanizales.tads.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(path = "location")
public class LocationController {

        @Autowired
        private LocationService locationService;
        @Autowired
        private ListSEService listSEService;
        @GetMapping
        public ResponseEntity<ResponseDTO>getAllLocations(){
            return new ResponseEntity<>(new ResponseDTO(200,
                    locationService.getLocations(),null), HttpStatus.OK);
        }
        @GetMapping(path = "/countries")
        public ResponseEntity<ResponseDTO>getCountries(){
            return new ResponseEntity<>(new ResponseDTO(200,
                    locationService.getLocationsByCodesize(3),null),HttpStatus.OK);
        }

        @GetMapping(path = "/departments")
        public ResponseEntity<ResponseDTO>getDepartments(){
            return new ResponseEntity<>(new ResponseDTO(200
                    ,locationService.getLocationsByCodesize(2),null),HttpStatus.OK);

        }

    }

