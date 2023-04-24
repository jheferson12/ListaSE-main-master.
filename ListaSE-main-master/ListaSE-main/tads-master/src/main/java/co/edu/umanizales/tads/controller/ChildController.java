package co.edu.umanizales.tads.controller;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.ReportDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/children")
public class ChildController {
    private final List<KidDTO> kid = new ArrayList<>();

    @GetMapping
    public List<KidDTO> getAllChildren() {
        return kid;
    }

    @GetMapping(path = "/{id}")
    public KidDTO getChildById(@PathVariable int id) {
        for (KidDTO kidDTO : kid) {
            if (kidDTO.getId() == id) {
                return kidDTO;
            }
        }
        throw new kidNotFoundException(id);
    }

    @PostMapping(path = ("/updatekid"))
    public KidDTO updateKid(@PathVariable int id, @RequestBody KidDTO updateKid) {
        for (int i = 0; i < kid.size(); i++) {
            KidDTO kidDTO = kid.get(i);
            if (kidDTO.getId() == id) {
                updateKid.setId(id);
                kid.set(i, updateKid);
                return updateKid;
            }
        }
        throw new kidNotFoundException(id);

    }

    @DeleteMapping("/{id}")
    public void deleteKid(@PathVariable int id) {
        for (int i = 0; i < kid.size(); i++) {
            ReportDTO reportDTO = kid.get(i);
            if (reportDTO.getId() == id) {
                kid.remove(i);
                return;
            }
        }
        throw new kidNotFoundException(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class kidNotFoundException extends RuntimeException {
        public kidNotFoundException(int id) {
            super("Child with id " + id + " not found.");
        }
    }
}
