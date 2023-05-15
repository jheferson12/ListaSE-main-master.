package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.ListDECircular;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListCircularService {
    private ListDECircular petsde;

    public ListCircularService() {
        this.petsde = new ListDECircular();


    }
}
