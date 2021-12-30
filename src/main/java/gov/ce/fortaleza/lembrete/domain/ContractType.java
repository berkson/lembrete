package gov.ce.fortaleza.lembrete.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:08
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contracts_type")
@AttributeOverride(name = "id", column = @Column(name = "contract_type_id"))
public class ContractType extends BaseDescriptionClass implements Comparable<ContractType> {

    @Builder
    public ContractType(Long id, String description, Integer maxValidity, List<Alert> alerts) {
        super(id, description);
        this.maxValidity = maxValidity;
        this.alerts = alerts;
    }


    private Integer maxValidity;
    @ManyToMany
    @JoinTable(name = "types_alerts",
            joinColumns = @JoinColumn(name = "contract_type_id"),
            inverseJoinColumns = @JoinColumn(name = "alert_id"))
    private List<Alert> alerts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractType ct = (ContractType) o;
        return Objects.equals(this.getId(), ct.getId()) &&
                Objects.equals(maxValidity, ct.maxValidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), maxValidity);
    }

    @Override
    public int compareTo(ContractType contractType) {
        Collator brCollator = Collator.getInstance();
        brCollator.setStrength(Collator.PRIMARY);
        return brCollator.compare(this.getDescription(), contractType.getDescription());
    }
}
