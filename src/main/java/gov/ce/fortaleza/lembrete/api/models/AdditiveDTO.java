package gov.ce.fortaleza.lembrete.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ce.fortaleza.lembrete.validations.annotations.SixtyMonthsVerification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 20:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SixtyMonthsVerification
public class AdditiveDTO {
    @JsonProperty(value = "contract_dto")
    private ContractDTO contract;
    @Min(1)
    @Max(60)
    @Digits(integer = 2, fraction = 0)
    private int deadline;
}
