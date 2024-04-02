package BackendSiadseUfps.siadse.controller;


import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.service.PQRSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/pqrs")
public class PQRSController {

    @Autowired
    PQRSService pqrsService;

    @PostMapping("/create")
    public ResponseEntity<PQRSDTO> createRequest(@RequestBody PQRSDTO pqrsDTO, @RequestParam Integer semilleroID) {

        PQRSDTO newPQRS = pqrsService.createPQRS(pqrsDTO, semilleroID);

        return ResponseEntity.ok(newPQRS);
    }
}
