package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
public class ContractTypeDTO extends BaseClass implements Serializable {
    private static final long serialVersionUID = 5752264780352184301L;
    @Length(min = 5, max = 255)
    private String description;
    private Integer maxValidity;
}
