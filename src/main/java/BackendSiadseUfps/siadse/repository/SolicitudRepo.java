package BackendSiadseUfps.siadse.repository;


import BackendSiadseUfps.siadse.entity.SolicitudIngresoSemillero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepo extends JpaRepository<SolicitudIngresoSemillero, Integer> {
}
