package BackendSiadseUfps.siadse.repository;

import BackendSiadseUfps.siadse.entity.EstadosSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadosSoliRepo  extends JpaRepository<EstadosSolicitud, Integer> {

    EstadosSolicitud findByEstado (String estado);


}
