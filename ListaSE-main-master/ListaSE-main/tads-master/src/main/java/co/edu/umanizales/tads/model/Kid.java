package co.edu.umanizales.tads.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Data
@Getter
@Setter
public class Kid {

    private String name;
    private byte age;
    private char gender;
    private Location location;
    private  String id;

    public Kid(String id, String name, int sum) {
    }


    public int getPosition() {
        return 0;
    }
}
