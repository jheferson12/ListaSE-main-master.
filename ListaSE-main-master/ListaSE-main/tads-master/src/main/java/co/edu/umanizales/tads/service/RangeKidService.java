package co.edu.umanizales.tads.service;
import co.edu.umanizales.tads.controller.dto.Ranges;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class RangeKidService {
    private List<Ranges> rangesList;

    public RangeKidService() {
        rangesList = new ArrayList<>();
        rangesList.add(new Ranges(1, 2));
        rangesList.add(new Ranges(5, 7));
        rangesList.add(new Ranges(10, 9));
        rangesList.add(new Ranges(9, 20));
        rangesList.add(new Ranges(12, 15));
    }
}
