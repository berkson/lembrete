package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:08
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contracts_type")
@AttributeOverride(name = "id", column = @Column(name = "contract_type_id"))
public class ContractType extends BaseDescriptionClass {
    private Integer maxValidity;
    @OneToMany
    @JoinTable(name = "types_alerts",
            joinColumns = @JoinColumn(name = "contract_type_id"),
            inverseJoinColumns = @JoinColumn(name = "alert_id"))
    private List<Alert> alerts = new ArrayList<>();
}
