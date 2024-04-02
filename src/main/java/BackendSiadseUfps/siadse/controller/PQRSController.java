package BackendSiadseUfps.siadse.controller;


import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.models.responses.Response;
import BackendSiadseUfps.siadse.service.PQRSService;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/pqrs")
public class PQRSController {

    @Autowired
    PQRSService pqrsService;

    @PostMapping("/create")
    public ResponseEntity<PQRSDTO> createRequest(@RequestBody PQRSDTO pqrsDTO, @RequestParam Integer semilleroID, @RequestParam Integer tipoPQRSID) {

        PQRSDTO newPQRS = pqrsService.createPQRS(pqrsDTO, semilleroID, tipoPQRSID);

        return ResponseEntity.ok(newPQRS);
    }


}
