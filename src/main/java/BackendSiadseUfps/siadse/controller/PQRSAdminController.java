package BackendSiadseUfps.siadse.controller;

import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.models.responses.Response;
import BackendSiadseUfps.siadse.service.PQRSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/pqrs")
public class PQRSAdminController {

    @Autowired
    PQRSService pqrsService;

    @GetMapping
    public List<PQRSDTO> listarPQRSr() {
        return pqrsService.listarPQRS();
    }

    @GetMapping("/tipo")
    public List<PQRSDTO> listarPqrsTipo(@RequestParam Integer tipo) {
        return pqrsService.listarPQRSporTipo(tipo);
    }

    @GetMapping("/estado")
    public List<PQRSDTO> listarPqrsEstado(@RequestParam Integer estado) {
        return pqrsService.listarPQRSporEstado(estado);
    }

    @PostMapping("/revision")
    public Response reviewPQRS(@RequestParam Integer pqrsId) {
        Response response = new Response();
        try {
            pqrsService.cambioEstadoPQRS(pqrsId, 3);
            response.setMessage("PQRS pasada a Revision");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error cambiando el estado: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/resuelto")
    public Response finishPQRS(@RequestParam Integer pqrsId) {
        Response response = new Response();
        try {
            pqrsService.cambioEstadoPQRS(pqrsId, 2);
            response.setMessage("PQRS Resuelta");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error cambiando el estado: " + e.getMessage());
        }

        return response;
    }

}
