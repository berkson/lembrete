package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseDescriptionClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 22:33
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractTypeDTO extends BaseDescriptionClass implements Serializable {
    private static final long serialVersionUID = 5752264780352184301L;
    private Integer maxValidity;
}
