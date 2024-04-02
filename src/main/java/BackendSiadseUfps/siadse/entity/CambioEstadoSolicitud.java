package BackendSiadseUfps.siadse.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "cambio_estado_solicitud")
public class CambioEstadoSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private SolicitudIngresoSemillero solicitud;

    @ManyToOne
    @JoinColumn(name = "id_estados", nullable = false)
    private EstadosSolicitud estado;

    private Date fecha_cambio;

}
