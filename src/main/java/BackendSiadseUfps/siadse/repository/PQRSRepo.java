package BackendSiadseUfps.siadse.repository;

import BackendSiadseUfps.siadse.dto.PQRSDTO;
import BackendSiadseUfps.siadse.entity.EstadosPQRS;
import BackendSiadseUfps.siadse.entity.PQRS;
import BackendSiadseUfps.siadse.entity.TiposPQRS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PQRSRepo extends JpaRepository<PQRS, Integer> {

    List<PQRS> findByTipoPqrs (TiposPQRS tiposPqrs);
    List<PQRS> findByEstadoRadicado (EstadosPQRS estadosPQRS);



}
