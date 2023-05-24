package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class LocationBathedQuantityPetDTO {
    private String city;
    private List<BathedPetsQuantityDTO> batheddogs;
    private int total;


    public LocationBathedQuantityPetDTO(String city) {
        this.city = city;
        this.total = 0;
        this.batheddogs = new ArrayList<>();
        this.batheddogs.add(new BathedPetsQuantityDTO(true, 0));
        this.batheddogs.add(new BathedPetsQuantityDTO(false, 0));
    }


}
