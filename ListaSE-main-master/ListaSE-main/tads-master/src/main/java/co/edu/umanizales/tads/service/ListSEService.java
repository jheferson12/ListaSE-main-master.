package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;




@Service
@Data
public class ListSEService {
    private ListSE kids;


    public ListSEService() {
        this.kids = new ListSE();
    }



    //---------------------------------------ESTE ES EL PRIMER METODO DE PROGRAMACION------------------------




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








