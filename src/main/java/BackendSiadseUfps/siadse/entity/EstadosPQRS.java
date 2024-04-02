package BackendSiadseUfps.siadse.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "estados_pqrs")
public class EstadosPQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;


}
