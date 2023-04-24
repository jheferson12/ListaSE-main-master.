package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportDTO {
    public Location location;
    public byte age;
    private  int id;
    private char gender;

    public void setName(Kid data) {
    }
}
