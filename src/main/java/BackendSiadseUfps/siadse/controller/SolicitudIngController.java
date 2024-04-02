package BackendSiadseUfps.siadse.controller;

import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.dto.SolicitudIngDTO;
import BackendSiadseUfps.siadse.service.SolicitudIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/solicitud")
public class SolicitudIngController {

    @Autowired
    SolicitudIngresoService solicitudIngresoService;

    @PostMapping("/create")
    public ResponseEntity<SolicitudIngDTO> createRequest(@RequestParam Integer userID, @RequestParam Integer semilleroID) {

        SolicitudIngDTO newSolicitudIngDTO = solicitudIngresoService.createSolicitud(userID, semilleroID);

        return ResponseEntity.ok(newSolicitudIngDTO);
    }

}
