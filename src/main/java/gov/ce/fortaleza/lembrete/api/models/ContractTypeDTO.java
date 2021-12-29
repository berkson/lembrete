package gov.ce.fortaleza.lembrete.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 22:33
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractTypeDTO {
    private Long id;
    private String description;
    private Integer maxValidity;
}
