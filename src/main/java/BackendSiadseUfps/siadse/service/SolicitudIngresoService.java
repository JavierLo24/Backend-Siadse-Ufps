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

import java.util.Date;

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

    public void cambiosEstadoSolicitud(SolicitudIngresoSemillero solicitudIngresoSemillero, EstadosSolicitud estado){
        CambioEstadoSolicitud cambioEstadoSolicitud = new CambioEstadoSolicitud();
        cambioEstadoSolicitud.setFecha_cambio(new Date());
        cambioEstadoSolicitud.setSolicitud(solicitudIngresoSemillero);
        cambioEstadoSolicitud.setEstado(estado);
    }


}
