package co.edu.umanizales.tads.service;
import co.edu.umanizales.tads.controller.ListDEController;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDEService {
    private ListDE Pets;

    public ListDEService() {


    }

    public void removeNodeBtIdentificationPet(String identification) {
    }
}


