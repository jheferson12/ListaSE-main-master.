package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class PetDTO {

    @NotBlank(message = "No se va ha anular el nombre de la mascota")
    private String name;

    @Min(value = 0,message = "la edad de la mascota tiene como minimo 1 ")
    @Max(value = 15,message = "la edad de la mascota tiene hasta maximo 15 ")
    private byte age;

    @NotBlank(message = "No se va ha anular la raza de la mascota ")
    private String breed;

    @NotNull(message = "No se va ha anular el genero de la mascota ")
    private char gender;

    @NotNull(message = "No es permitido anular la ubicacion del perro ")
    private String codeLocationPet;
    @NotBlank(message = "No es permitido anular el id de la mascota ")
    private String id;

    // constructores, getters y setters
}

