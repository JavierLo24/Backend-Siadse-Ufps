package BackendSiadseUfps.siadse.service;

import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.dto.SemilleroDTO;
import BackendSiadseUfps.siadse.entity.CambioEstadoRadicado;
import BackendSiadseUfps.siadse.entity.EstadosPQRS;
import BackendSiadseUfps.siadse.entity.PQRS;
import BackendSiadseUfps.siadse.entity.Semillero;
import BackendSiadseUfps.siadse.repository.EstadosPQRSRepo;
import BackendSiadseUfps.siadse.repository.PQRSRepo;
import BackendSiadseUfps.siadse.repository.SemilleroRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("pqrsService")
public class PQRSService {

    @Autowired
    PQRSRepo pqrsRepo;

    @Autowired
    EstadosPQRSRepo estadosPQRSRepo;

    @Autowired
    SemilleroRepo semilleroRepo;

    public PQRSDTO createPQRS(PQRSDTO pqrsDTO, Integer semilleroID){
        PQRS pqrs = new PQRS();
        BeanUtils.copyProperties(pqrsDTO, pqrs);

        EstadosPQRS estado = estadosPQRSRepo.findByEstado("pendiente");
        Semillero semillero = semilleroRepo.findById(semilleroID).orElse(null);
        if (semillero == null)
            throw new IllegalArgumentException("Semillero no existe");

        pqrs.setEstado_radicado(estado);
        pqrs.setFecha_radicado(new Date());
        pqrs.setSemillero(semillero);

        if(pqrsDTO.getAnonimo()){
            if (pqrs.getNombre() == null || pqrs.getNombre() == "") {
                throw new IllegalArgumentException("El nombre de la persona no puede estar vacío.");
            }
            if (pqrs.getApellido() == null || pqrs.getApellido() == "") {
                throw new IllegalArgumentException("El apellido de la persona no puede estar vacío.");
            }
            if (pqrs.getCedula() == null || pqrs.getCedula() == "") {
                throw new IllegalArgumentException("La cédula de la persona no puede estar vacío.");
            }
        }

        PQRS pqrsRadicado = pqrsRepo.save(pqrs);

        cambiosEstadoPQRS(pqrsRadicado, estado);

        PQRSDTO creadoPQRS = new PQRSDTO();
        BeanUtils.copyProperties(pqrsRadicado, creadoPQRS);

        return creadoPQRS;

    }


    public void cambiosEstadoPQRS(PQRS pqrs, EstadosPQRS estado){
        CambioEstadoRadicado cambioEstadoRadicado = new CambioEstadoRadicado();
        cambioEstadoRadicado.setFecha_cambio(new Date());
        cambioEstadoRadicado.setPqrs(pqrs);
        cambioEstadoRadicado.setEstado(estado);
    }


}
