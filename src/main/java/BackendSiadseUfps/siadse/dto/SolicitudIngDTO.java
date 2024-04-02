package BackendSiadseUfps.siadse.dto;

import BackendSiadseUfps.siadse.entity.EstadosSolicitud;
import BackendSiadseUfps.siadse.entity.OurUsers;
import BackendSiadseUfps.siadse.entity.Semillero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudIngDTO {

   // private Integer id;

    private OurUsers usuario;

    private Semillero id_semillero;

    private Date fecha_creacion;

    private Date fecha_actualizacion;

    private EstadosSolicitud estado;

}
