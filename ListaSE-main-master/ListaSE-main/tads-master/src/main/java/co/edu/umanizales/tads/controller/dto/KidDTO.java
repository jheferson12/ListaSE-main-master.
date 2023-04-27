package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor

public class KidDTO {
    private String identification;
    private String name;
    private byte age;
    private char gender;
    private String codeLocation;



}
