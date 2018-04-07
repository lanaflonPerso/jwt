package co.simplon.filrouge.controleur;


import co.simplon.filrouge.service.PeopleLinkService;
import co.simplon.filrouge.service.PieceLinkService;
import co.simplon.filrouge.service.VehiculeLinkService;

import co.simplon.filrouge.service.WeaponLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class junctionControlleur {

    @Autowired
    private VehiculeLinkService vehiculeLinkService;

    @RequestMapping(value = "/linkVehicule/{idCase}/{idVehicule}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVehiculeLink(@PathVariable Long idCase, @PathVariable Long idVehicule ){
        try {

            vehiculeLinkService.deleteLinkVehicule(idCase,idVehicule );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Autowired
    private PeopleLinkService peopleLinkService;

    @RequestMapping(value = "/linkPeople/{idCase}/{idPeople}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePeopleLink(@PathVariable Long idCase, @PathVariable Long idPeople ){
        try {

            peopleLinkService.deleteLinkPeople(idCase,idPeople );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Autowired
    private WeaponLinkService weaponLinkService;

    @RequestMapping(value = "/linkWeapon/{idCase}/{idWeapon}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWeaponLink(@PathVariable Long idCase, @PathVariable Long idWeapon ){
        try {

            weaponLinkService.deleteLinkWeapon(idCase,idWeapon );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Autowired
    private PieceLinkService pieceLinkService;

    @RequestMapping(value = "/linkPiece/{idCase}/{idPiece}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePieceLink(@PathVariable Long idCase, @PathVariable Long idPiece ){
        try {

            pieceLinkService.deleteLinkPiece(idCase,idPiece );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
