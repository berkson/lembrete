package gov.ce.fortaleza.lembrete.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by berkson
 * Date: 11/01/2022
 * Time: 18:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractTypesDTO {
    @JsonProperty(value = "contract_types")
    private List<ContractTypeDTO> contractTypeDTOS;
}
