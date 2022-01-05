package gov.ce.fortaleza.lembrete.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
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
public class ContractDTO extends BaseClass implements Serializable {
    private static final long serialVersionUID = -7864511284395161859L;
    @Length(min = 5, max = 255)
    @JsonProperty(value = "contract_number")
    private String contractNumber;
    private CompanyDTO company;
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "initial_date")
    private LocalDate initialDate;
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "final_date")
    private LocalDate finalDate;
    @JsonProperty(value = "contract_type")
    private ContractTypeDTO contractType;
    @NotEmpty
    @JsonProperty(value = "interested_list")
    private List<InterestedDTO> interestedList;
}

