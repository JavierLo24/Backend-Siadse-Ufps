package BackendSiadseUfps.siadse.controller;


import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.dto.SolicitudIngDTO;
import BackendSiadseUfps.siadse.models.responses.Response;
import BackendSiadseUfps.siadse.service.SolicitudIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/solicitud")
public class SolicitudIngAdminController {

    @Autowired
    SolicitudIngresoService solicitudIngresoService;


    @GetMapping
    public List<SolicitudIngDTO> listarSolicitudes() {
        return solicitudIngresoService.listarSolicitudes();
    }

    @GetMapping("/estado")
    public List<SolicitudIngDTO> listarSolicitudesEstado(@RequestParam Integer estado) {
        return solicitudIngresoService.listarSolicitudporEstado(estado);
    }

    @PostMapping("/revision")
    public Response reviewSolicitud(@RequestParam Integer solicitudID) {
        Response response = new Response();
        try {
            solicitudIngresoService.cambioEstadoSolicitud(solicitudID, 3);
            response.setMessage("Solicitud Aprobada");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error cambiando el estado: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/resuelto")
    public Response finishSolicitud(@RequestParam Integer solicitudID) {
        Response response = new Response();
        try {
            solicitudIngresoService.cambioEstadoSolicitud(solicitudID, 2);
            response.setMessage("Solicitud Rechazada");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error cambiando el estado: " + e.getMessage());
        }

        return response;
    }

}
