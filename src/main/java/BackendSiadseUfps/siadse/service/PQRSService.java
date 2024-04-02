package BackendSiadseUfps.siadse.service;

import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.dto.SemilleroDTO;
import BackendSiadseUfps.siadse.entity.*;
import BackendSiadseUfps.siadse.repository.EstadosPQRSRepo;
import BackendSiadseUfps.siadse.repository.PQRSRepo;
import BackendSiadseUfps.siadse.repository.SemilleroRepo;
import BackendSiadseUfps.siadse.repository.TipoPQRSRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("pqrsService")
public class PQRSService {

    @Autowired
    PQRSRepo pqrsRepo;

    @Autowired
    EstadosPQRSRepo estadosPQRSRepo;

    @Autowired
    SemilleroRepo semilleroRepo;

    @Autowired
    TipoPQRSRepo tipoPQRSRepo;


    public PQRSDTO createPQRS(PQRSDTO pqrsDTO, Integer semilleroID, Integer tipoPQRSID){
        PQRS pqrs = new PQRS();
        BeanUtils.copyProperties(pqrsDTO, pqrs);

        TiposPQRS tiposPQRS = tipoPQRSRepo.findById(tipoPQRSID).orElse(null);
        if (tiposPQRS == null)
            throw new IllegalArgumentException("Ese tipo de PQRS no existe");
        EstadosPQRS estado = estadosPQRSRepo.findByEstado("Pendiente");
        Semillero semillero = semilleroRepo.findById(semilleroID).orElse(null);
        if (semillero == null)
            throw new IllegalArgumentException("Semillero no existe");

        pqrs.setTipoPqrs(tiposPQRS);
        pqrs.setEstadoRadicado(estado);
        pqrs.setFechaRadicado(new Date());
        pqrs.setSemillero(semillero);
        pqrs.setCodigoRadicado(generateRandomCode(10));

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

    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString();
    }

    public void cambioEstadoPQRS(Integer pqrsID, Integer nuevoEstadoID){

        PQRS pqrs = pqrsRepo.findById(pqrsID).orElseThrow(() -> new IllegalArgumentException("PQRS not found"));
        EstadosPQRS nuevoEstado = estadosPQRSRepo.findById(nuevoEstadoID).orElseThrow(() -> new IllegalArgumentException("Estado not found"));

        switch (nuevoEstadoID) {

            case 2:
                if (!pqrs.getEstadoRadicado().getEstado().equals("Revision")) {
                    throw new IllegalArgumentException(
                            "No se puede dar por resuelto este PQRS si no está en revisión");
                }
                break;

            case 3:

            if (!pqrs.getEstadoRadicado().getEstado().equals("Pendiente")) {
                throw new IllegalArgumentException(
                        "No se puede revisar este PQRS si no está en pendiente");
            }
            break;

            default:
                throw new IllegalArgumentException("Something is wrong with states");

        }
        pqrs.setEstadoRadicado(nuevoEstado);
        cambiosEstadoPQRS(pqrs, nuevoEstado);
        pqrsRepo.save(pqrs);

    }

    public List<PQRSDTO> listarPQRS() {
        return pqrsRepo.findAll().stream().map(pqrs -> {
            new PQRSDTO();
            return PQRSDTO.builder()
                    .id(pqrs.getId())
                    .titulo(pqrs.getTitulo())
                    .descripcion(pqrs.getDescripcion())
                    .fechaRadicado(pqrs.getFechaRadicado())
                    .estadoRadicado(pqrs.getEstadoRadicado())
                    .correo(pqrs.getCorreo())
                    .tipoPqrs(pqrs.getTipoPqrs())
                    .anonimo(pqrs.getAnonimo())
                    .nombre(pqrs.getNombre())
                    .apellido(pqrs.getApellido())
                    .cedula(pqrs.getCedula())
                    .codigoRadicado(pqrs.getCodigoRadicado())
                    .semillero(pqrs.getSemillero())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<PQRSDTO> listarPQRSporTipo(Integer tipoID) {
        TiposPQRS tiposPQRS = tipoPQRSRepo.findById(tipoID).orElse(null);
        List<PQRS> pqrss = pqrsRepo.findByTipoPqrs(tiposPQRS);

        List<PQRSDTO> PQRSDTOs = new ArrayList<>();

        for (PQRS pqrs : pqrss) {
            PQRSDTO pqrsdto = new PQRSDTO();
            pqrsdto.setFechaRadicado(pqrs.getFechaRadicado());
            pqrsdto.setEstadoRadicado(pqrs.getEstadoRadicado());
            BeanUtils.copyProperties(pqrs, pqrsdto);
            PQRSDTOs.add(pqrsdto);
        }

        return PQRSDTOs;
    }

    public List<PQRSDTO> listarPQRSporEstado(Integer estadoID) {
        EstadosPQRS estadosPQRS = estadosPQRSRepo.findById(estadoID).orElse(null);
        List<PQRS> pqrss = pqrsRepo.findByEstadoRadicado(estadosPQRS);

        List<PQRSDTO> PQRSDTOs = new ArrayList<>();

        for (PQRS pqrs : pqrss) {
            PQRSDTO pqrsdto = new PQRSDTO();
            pqrsdto.setFechaRadicado(pqrs.getFechaRadicado());
            pqrsdto.setEstadoRadicado(pqrs.getEstadoRadicado());
            BeanUtils.copyProperties(pqrs, pqrsdto);
            PQRSDTOs.add(pqrsdto);
        }

        return PQRSDTOs;
    }


    public void cambiosEstadoPQRS(PQRS pqrs, EstadosPQRS estado){
        CambioEstadoRadicado cambioEstadoRadicado = new CambioEstadoRadicado();
        cambioEstadoRadicado.setFecha_cambio(new Date());
        cambioEstadoRadicado.setPqrs(pqrs);
        cambioEstadoRadicado.setEstado(estado);
    }


}
