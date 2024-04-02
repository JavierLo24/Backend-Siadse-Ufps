package BackendSiadseUfps.siadse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemilleroDTO {

    //private Integer id;
    private String name;
    private Integer id_grupo;
    private Integer id_lider;
    private Integer lineaInvestigacion;
    private String descripcion;
    private String Logo;

}
