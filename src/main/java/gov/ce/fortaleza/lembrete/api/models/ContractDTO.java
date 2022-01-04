package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 20:24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO extends BaseClass {
    private String contractNumber;
    private CompanyDTO company;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private ContractTypeDTO contractType;
    private List<InterestedDTO> interestedList;
}

