package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false)
    private Company company;
    private LocalDate initialDate;
    private LocalDate finalDate;
    @OneToOne
    @JoinColumn(name = "contract_type_id", referencedColumnName = "contract_type_id", nullable = false)
    private ContractType contractType;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "contacts", joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "interested_id", referencedColumnName = "interested_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"contract_id", "interested_id"})})
    private List<Interested> interestedList = new ArrayList<>();
}
