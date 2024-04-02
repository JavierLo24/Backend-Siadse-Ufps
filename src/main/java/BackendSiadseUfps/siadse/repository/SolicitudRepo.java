package BackendSiadseUfps.siadse.repository;


import BackendSiadseUfps.siadse.entity.EstadosPQRS;
import BackendSiadseUfps.siadse.entity.EstadosSolicitud;
import BackendSiadseUfps.siadse.entity.PQRS;
import BackendSiadseUfps.siadse.entity.SolicitudIngresoSemillero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepo extends JpaRepository<SolicitudIngresoSemillero, Integer> {

    List<SolicitudIngresoSemillero> findByEstado (EstadosSolicitud estadosSolicitud);
}
