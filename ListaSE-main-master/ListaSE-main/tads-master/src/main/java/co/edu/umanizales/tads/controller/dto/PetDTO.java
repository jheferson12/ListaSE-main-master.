package co.edu.umanizales.tads.controller.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class PetDTO {
    @NotEmpty(message = "No se permite  que este vacio la identificacion")
    private String identificationPet;

    @NotEmpty(message = "No se permite que este vacio el nombre")
    private String name;

    @Min(value = 0,message = "No hay minimo")
    @Max(value = 15,message = "lo maximo de letras es 15 ")
    private byte age;

    @Pattern(regexp = "[A-Za-z]+$\"",message = "El nombre solo puede contener numeros")
    private char gender;

    @Size(min = 0, max = 19,message = "el minimo es 0 y el maximo  cantidad de numeros en string es 19 ")
    private String codeLocation;

    // constructores, getters y setters
}

