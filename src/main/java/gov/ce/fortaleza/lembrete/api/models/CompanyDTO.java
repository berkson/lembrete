package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String cnpj;
    private String name;
}
