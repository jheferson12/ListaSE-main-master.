package co.edu.umanizales.tads.service;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
public class ListSEService {


    private ListSE kids;


    public ListSEService() {


    }


    public void losePositions(String id,int lose){

    }
    //---------------------------------------ESTE ES EL PRIMER METODO DE PROGRAMACION------------------------
    public void invert() {
        kids.invert();
    }
    public void addToStartNameChar(String id,String name){

    }
    public  void removeKidByAge(byte age) {


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
    public int getPostById(String id) {
        return getPostById("123456");
    }



}







