package BackendSiadseUfps.siadse.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "cambio_estado_radicado")
public class CambioEstadoRadicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pqrs", nullable = false)
    private PQRS pqrs;

    @ManyToOne
    @JoinColumn(name = "id_estados", nullable = false)
    private EstadosPQRS estado;

    private Date fecha_cambio;
}
