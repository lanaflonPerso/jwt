package co.simplon.filrouge.controleur;


import co.simplon.filrouge.modele.FingerPrint;
import co.simplon.filrouge.repository.FingerPrintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FingerPrintControleur {

    @Autowired
    FingerPrintRepository fingerPrintRepository;

    // Consulter toutes les FingerPrint
    @GetMapping("/fingerprint")
    public List<FingerPrint> getAllFingerPrint() {
        return fingerPrintRepository.findAll();
    }

    // Creer une nouvelle FingerPrint
    @PostMapping("/fingerprint")
    public FingerPrint createFingerPrint(@Valid @RequestBody FingerPrint fingerPrint) {
        return fingerPrintRepository.save ( fingerPrint );
    }

    // Consulter une seule FingerPrint
    @GetMapping("/fingerprint/{id}")
    public ResponseEntity<FingerPrint> getFingerPrintById(@PathVariable(value = "id") Long fingerPrintId) {
        FingerPrint fingerPrint = fingerPrintRepository.findOne (fingerPrintId);
        if(fingerPrint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(fingerPrint);
    }

    // Mise à jour d'une FingerPrint
    @PutMapping("/fingerprint/{id}")
    public ResponseEntity<FingerPrint> updateFingerPrint(@PathVariable(value = "id") Long fingerPrintId,
                                                  @Valid @RequestBody FingerPrint fingerPrintDetail) {
        FingerPrint fingerPrint = fingerPrintRepository.findOne(fingerPrintId);
        if(fingerPrint == null) {
            return ResponseEntity.notFound().build();
        }
        fingerPrint.setFingerprint (fingerPrintDetail.getFingerprint ());
        fingerPrint.setPeople(fingerPrintDetail.getPeople());

        FingerPrint updatedFingerPrint = fingerPrintRepository.save(fingerPrint);
        return ResponseEntity.ok(updatedFingerPrint);
    }


    // Efface une FingerPrint
    @DeleteMapping("/fingerprint/{id}")
    public ResponseEntity<FingerPrint> deleteFingerPrint(@PathVariable(value = "id") Long fingerPrintId) {
        FingerPrint fingerPrint = fingerPrintRepository.findOne(fingerPrintId);
        if(fingerPrint == null) {
            return ResponseEntity.notFound().build();
        }

        fingerPrintRepository.delete(fingerPrint);
        return ResponseEntity.ok().build();
    }
}