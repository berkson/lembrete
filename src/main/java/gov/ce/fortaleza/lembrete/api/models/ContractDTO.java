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

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Length(min = 6, max = 255)
    @JsonProperty(value = "contract_number")
    private String contractNumber;
    @NotNull
    @Valid
    private CompanyDTO company;
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "initial_date")
    private LocalDate initialDate;
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "final_date")
    @Future
    private LocalDate finalDate;
    @NotNull
    @JsonProperty(value = "contract_type")
    @Valid
    private ContractTypeDTO contractType;
    @NotEmpty
    @JsonProperty(value = "interested_list")
    @Valid
    private List<InterestedDTO> interestedList;
}

