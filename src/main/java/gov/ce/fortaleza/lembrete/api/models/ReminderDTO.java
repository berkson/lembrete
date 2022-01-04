package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 21:09
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReminderDTO extends BaseClass implements Serializable {
    private static final long serialVersionUID = 7827967205309389928L;
    private ContractDTO contract;
    private CompanyDTO company;
    private LocalDate initialDate;
    private LocalDate extendedDate;
    private List<InterestedDTO> interestedList;

}
