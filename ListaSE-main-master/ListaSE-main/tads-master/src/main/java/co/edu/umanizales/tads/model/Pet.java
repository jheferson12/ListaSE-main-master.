package co.edu.umanizales.tads.model;
import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@Data
@Getter
@Setter
public class Pet {
    @NotBlank
    private String name;

    @Min(0)
    @Max(127)
    private byte age;

    @NotBlank
    private String breed;

    @NotNull
    private char gender;

    @NotNull
    private Location location;

    @NotBlank
    private String id;

    public Pet(String id, String name, int sum) {
    }

    public int getPosition() {
        return 0;
    }
}

