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



}

