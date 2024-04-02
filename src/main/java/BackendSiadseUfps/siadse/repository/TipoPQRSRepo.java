package BackendSiadseUfps.siadse.repository;

import BackendSiadseUfps.siadse.entity.PQRS;
import BackendSiadseUfps.siadse.entity.TiposPQRS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPQRSRepo  extends JpaRepository<TiposPQRS, Integer> {

    TiposPQRS findByTipo (String tipo);

}
