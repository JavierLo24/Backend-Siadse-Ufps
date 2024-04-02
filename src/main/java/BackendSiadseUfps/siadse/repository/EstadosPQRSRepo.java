package BackendSiadseUfps.siadse.repository;

import BackendSiadseUfps.siadse.entity.EstadosPQRS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadosPQRSRepo extends JpaRepository<EstadosPQRS, Integer> {

    EstadosPQRS findByEstado (String estado);
}
