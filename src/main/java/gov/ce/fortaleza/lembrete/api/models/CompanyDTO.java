package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 20:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO extends BaseClass {
    private static final long serialVersionUID = 5309254162188499126L;
    @CNPJ
    private String cnpj;
    @Length(min = 5, max = 255)
    private String name;
}
