package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 22:10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "contract_id"))
@Table(name = "contracts")
public class Contract extends BaseClass {
    private String contractNumber;
    @OneToOne
    @JoinColumn(name = "contract_type_id", referencedColumnName = "contract_type_id")
    private ContractType contractType;
    @OneToOne(mappedBy = "contract")
    private Reminder reminder;
}
