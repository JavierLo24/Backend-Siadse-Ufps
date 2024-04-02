package BackendSiadseUfps.siadse.service;

import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.dto.SolicitudIngDTO;
import BackendSiadseUfps.siadse.entity.*;
import BackendSiadseUfps.siadse.repository.EstadosSoliRepo;
import BackendSiadseUfps.siadse.repository.OurUserRepo;
import BackendSiadseUfps.siadse.repository.SemilleroRepo;
import BackendSiadseUfps.siadse.repository.SolicitudRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("solicitudIngService")
public class SolicitudIngresoService {

    @Autowired
    SolicitudRepo solicitudRepo;

    @Autowired
    EstadosSoliRepo estadosSoliRepo;

    @Autowired
    OurUserRepo ourUserRepo;

    @Autowired
    SemilleroRepo semilleroRepo;

    public SolicitudIngDTO createSolicitud(Integer userID, Integer semilleroID){

        SolicitudIngresoSemillero solicitudIngresoSemillero = new SolicitudIngresoSemillero();

        EstadosSolicitud estado = estadosSoliRepo.findByEstado("pendiente");
        OurUsers ourUsers = ourUserRepo.findById(userID).orElse(null);
        if (ourUsers == null)
            throw new IllegalArgumentException("Usuario no existe");
        Semillero semillero = semilleroRepo.findById(semilleroID).orElse(null);
        if (semillero == null)
            throw new IllegalArgumentException("Semillero no existe");

        solicitudIngresoSemillero.setEstado(estado);
        solicitudIngresoSemillero.setFecha_creacion(new Date());
        solicitudIngresoSemillero.setFecha_actualizacion(new Date());
        solicitudIngresoSemillero.setId_semillero(semillero);
        solicitudIngresoSemillero.setUsuario(ourUsers);


        SolicitudIngresoSemillero solicitudRadicada = solicitudRepo.save(solicitudIngresoSemillero);

        cambiosEstadoSolicitud(solicitudRadicada, estado);

        SolicitudIngDTO solicitudCreada = new SolicitudIngDTO();
        BeanUtils.copyProperties(solicitudRadicada, solicitudCreada);

        return solicitudCreada;

    }

    public List<SolicitudIngDTO> listarSolicitudes() {
        return solicitudRepo.findAll().stream().map(solicitudIngresoSemillero -> {
            new SolicitudIngDTO();
            return SolicitudIngDTO.builder()
                    .id(solicitudIngresoSemillero.getId())
                    .usuario(solicitudIngresoSemillero.getUsuario())
                    .id_semillero(solicitudIngresoSemillero.getId_semillero())
                    .fecha_creacion(solicitudIngresoSemillero.getFecha_creacion())
                    .fecha_actualizacion(solicitudIngresoSemillero.getFecha_actualizacion())
                    .estado(solicitudIngresoSemillero.getEstado())
                    .build();
        }).collect(Collectors.toList());
    }

    public void cambioEstadoSolicitud(Integer solicitudID, Integer nuevoEstadoID){

        SolicitudIngresoSemillero solicitudIngresoSemillero = solicitudRepo.findById(solicitudID).orElseThrow(() -> new IllegalArgumentException("Solicitud not found"));
        EstadosSolicitud nuevoEstado = estadosSoliRepo.findById(nuevoEstadoID).orElseThrow(() -> new IllegalArgumentException("Estado not found"));

        switch (nuevoEstadoID) {

            case 1:
                if (!solicitudIngresoSemillero.getEstado().getEstado().equals("Pendiente")) {
                    throw new IllegalArgumentException(
                            "No se puede rechazar esta solicitud si no está Pendiente");
                }
                break;

            case 3:

                if (!solicitudIngresoSemillero.getEstado().getEstado().equals("Pendiente")) {
                    throw new IllegalArgumentException(
                            "No se puede Aprobar esta solicitud si no está en pendiente");
                }
                break;

            default:
                throw new IllegalArgumentException("Something is wrong with states");

        }
        solicitudIngresoSemillero.setEstado(nuevoEstado);
        cambiosEstadoSolicitud(solicitudIngresoSemillero, nuevoEstado);
        solicitudRepo.save(solicitudIngresoSemillero);
    }

    public List<SolicitudIngDTO> listarSolicitudporEstado(Integer estadoID) {
        EstadosSolicitud estadosSolicitud = estadosSoliRepo.findById(estadoID).orElse(null);
        List<SolicitudIngresoSemillero> solicitudIngresoSemilleross = solicitudRepo.findByEstado(estadosSolicitud);

        List<SolicitudIngDTO> SolicitudIngDTOs = new ArrayList<>();

        for (SolicitudIngresoSemillero solicitudIngresoSemillero : solicitudIngresoSemilleross) {
            SolicitudIngDTO solicitudIngDTO = new SolicitudIngDTO();
//            pqrsdto.setFechaRadicado(solicitudIngresoSemillero.getFechaRadicado());
//            pqrsdto.setEstadoRadicado(solicitudIngresoSemillero.getEstadoRadicado());
            BeanUtils.copyProperties(solicitudIngresoSemillero, solicitudIngDTO);
            SolicitudIngDTOs.add(solicitudIngDTO);
        }

        return SolicitudIngDTOs;
    }

    public void cambiosEstadoSolicitud(SolicitudIngresoSemillero solicitudIngresoSemillero, EstadosSolicitud estado){
        CambioEstadoSolicitud cambioEstadoSolicitud = new CambioEstadoSolicitud();
        cambioEstadoSolicitud.setFecha_cambio(new Date());
        cambioEstadoSolicitud.setSolicitud(solicitudIngresoSemillero);
        cambioEstadoSolicitud.setEstado(estado);
    }


}
