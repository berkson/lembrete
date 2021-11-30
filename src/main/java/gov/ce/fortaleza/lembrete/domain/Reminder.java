package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reminder extends BaseClass{
    private ContractType contractType;
    private int actualValidity;
    private List<Alert> alerts;
}
