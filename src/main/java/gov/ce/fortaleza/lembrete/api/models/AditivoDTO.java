package gov.ce.fortaleza.lembrete.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 20:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AditivoDTO {
    @Valid
    private ContractDTO contract;
    @Size(min = 1, max = 60)
    private int deadline;
}
