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



    public Kid(String name, byte age, char gender, String codeLocation, String identification) {
    }


    public int getPosition() {
        return 0;
    }



}
