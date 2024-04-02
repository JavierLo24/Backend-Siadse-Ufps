package BackendSiadseUfps.siadse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pqrs")
public class PQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "titulo cannot be null")
    private String titulo;
    @NotNull(message = "descripcion cannot be null")
    private String descripcion;
    private Date fechaRadicado;
    @ManyToOne
    @JoinColumn(name = "id_estados", nullable = false)
    private EstadosPQRS estadoRadicado;
    @NotNull(message = "correo cannot be null")
    private String correo;
    @ManyToOne
    @JoinColumn(name = "id_tipos_pqrs", nullable = false)
    private TiposPQRS tipoPqrs;
    @NotNull(message = "anonimo cannot be null")
    private Boolean anonimo;
    private String nombre;
    private String apellido;
    private String cedula;
    @ManyToOne
    @JoinColumn(name = "id_semillero", nullable = false)
    private Semillero semillero;
    @NotNull(message = "codigoRadicado cannot be null")
    private String codigoRadicado;
}
