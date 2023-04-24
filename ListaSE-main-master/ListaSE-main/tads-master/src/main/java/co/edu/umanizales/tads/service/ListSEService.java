package co.edu.umanizales.tads.service;
import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;


@Service
@Data
public class ListSEService {


    private ListSE kids;


    public ListSEService() {


    }
    public void losePositions(String id,int lose){

    }
    public void addToStartNameChar(String id,String name){

    }
    public void deleteKidByAge(byte age){

    }
    public  String getAverageAge(){
        return getAverageAge();
    }
    public String changeExtremes(){
        return changeExtremes();
    }
    public String earnPositions(String id,int earn){
        return earnPositions("1234",23);
    }


}








