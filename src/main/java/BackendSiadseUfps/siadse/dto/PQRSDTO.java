package BackendSiadseUfps.siadse.dto;

import BackendSiadseUfps.siadse.entity.EstadosPQRS;
import BackendSiadseUfps.siadse.entity.Semillero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PQRSDTO {

//    private Integer id;
    private String titulo;
    private String descripcion;
    private Date fecha_radicado;
    private EstadosPQRS estado_radicado;
    private String correo;
    private String tipo_pqrs;
    private Boolean anonimo;
    private String nombre;
    private String apellido;
    private String cedula;
    private Semillero id_semillero;
    private String codigo_radicado;

}
