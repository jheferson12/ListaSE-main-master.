package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ReportPetsLocationBathedPetsDTO {
    private List<LocationBathedQuantityPetDTO> locationBathedQuantityPetDTOS;

    private int total;

    public ReportPetsLocationBathedPetsDTO(List<Location> cities) {
        locationBathedQuantityPetDTOS = new ArrayList<>();
        for(Location location: cities){
            locationBathedQuantityPetDTOS.add(new
                    LocationBathedQuantityPetDTO(location.getName()));
        }
    }

    // método actualizar
    public void updateQuantityPet(String city, boolean bathdog) {
        for (LocationBathedQuantityPetDTO loc : locationBathedQuantityPetDTOS) {
            if (loc.getCity().equals(city)) {
                for (BathedPetsQuantityDTO bathedPetsQuantityDTO : loc.getBatheddogs()) {
                    if (bathedPetsQuantityDTO.getBathdog() == bathdog) {
                        bathedPetsQuantityDTO.setQuantity(bathedPetsQuantityDTO.getQuantity() + 1);
                        loc.setTotal(loc.getTotal() + 1);
                        return;
                    }
                }
            }
        }
    }





}
