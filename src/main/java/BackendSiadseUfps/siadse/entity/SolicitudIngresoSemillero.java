package BackendSiadseUfps.siadse.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "solicitud_ingreso_semillero")
public class SolicitudIngresoSemillero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_ourusers", nullable = false)
    private OurUsers usuario;

    @ManyToOne
    @JoinColumn(name = "id_semillero", nullable = false)
    private Semillero id_semillero;

    private Date fecha_creacion;

    private Date fecha_actualizacion;

    @ManyToOne
    @JoinColumn(name = "id_estados", nullable = false)
    private EstadosSolicitud estado;
}
