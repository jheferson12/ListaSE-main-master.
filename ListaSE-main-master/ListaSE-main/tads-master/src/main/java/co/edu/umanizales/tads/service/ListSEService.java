package co.edu.umanizales.tads.service;
import co.edu.umanizales.tads.exception.ListSEException;
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
    public void invert() throws ListSEException {
        kids.invert();
    }

    public  void removeKidByAge(byte age) {


    }

    public void deleteKidByAge(byte age){

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


    public void addKidAtPosForLose(Kid kid, int pos) {
    }

    public void winPositionKid(String id, int win) {
    }


    private double getAverageAge(double age) {
        return getAverageAge(age);
    }

}








