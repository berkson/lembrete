package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.Company;
import gov.ce.fortaleza.lembrete.domain.Contract;
import gov.ce.fortaleza.lembrete.domain.Interested;
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
public class ReminderDTO implements Serializable {
    private static final long serialVersionUID = 7827967205309389928L;
    private Contract contract;
    private Company company;
    private LocalDate initialDate;
    private LocalDate extendedDate;
    private List<InterestedDTO> interestedList;

}
