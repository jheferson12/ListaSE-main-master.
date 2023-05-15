package co.edu.umanizales.tads.model;
import lombok.*;

@AllArgsConstructor
@Data
@Getter
@Setter
public class Kid {

    private String identification;
    private String name;
    private byte age;
    private char gender;
    private Location location;



}
