package co.edu.umanizales.tads.model;
import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class Pet {
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
    private Location location;

    @NotBlank(message = "No es permitido anular el id de la mascota ")
    private String id;


}

