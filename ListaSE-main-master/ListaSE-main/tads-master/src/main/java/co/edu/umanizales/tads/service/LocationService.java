package co.edu.umanizales.tads.service;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import co.edu.umanizales.tads.model.Location;
@Service
@Data
public class LocationService {
    private List<Location> locations;

    public LocationService(){

            locations=new ArrayList<>();
            locations.add(new Location("169","Colombia"));
            locations.add(new Location("16905","Antioquia"));
            locations.add(new Location("16917","Caldas"));
            locations.add(new Location("16993","Risaralda"));
            locations.add(new Location("16905001","Medellin"));
            //Conectar
    }

        public List<Location> getLocationsByCodesize(int size) {
            List<Location>listLocations=new ArrayList<>();
            for (Location loc:locations){
                if (loc.getCode().length()==size){
                    listLocations.add(loc );
                }
            }
            return listLocations;
        }
        public Location getLocationsByCode(String code){
            for (Location loc:locations){
                if (loc.getCode().equals(code)){
                    return loc;
                }
            }
            return null;
        }


    /*public Location getLocationByCode(String codeLocation) throws ListSEException {
        if (codeLocation == null || codeLocation.isEmpty()) {
            throw new IllegalArgumentException("El código de ubicación no puede ser nulo o vacío");
        }
        Node head=null;
        Node current = head;
        while (current != null) {
            Location currentLocation = current.getData().getCodeLocation();
            if (currentLocation != null && currentLocation.getCode().equals(codeLocation)) {
                return currentLocation;
            }
            current = current.getNext();
        }
        throw new ListSEException("No se encontró ninguna ubicación con el código proporcionado");
    }

     */

    public List<Location> getLocationsByCodeSize(int size){
        List<Location> listLocations = new ArrayList<>();
        for(Location loc: locations){
            if(loc.getCode().length()==size) {
                listLocations.add(loc);
            }
        }
        return listLocations;
    }



}


