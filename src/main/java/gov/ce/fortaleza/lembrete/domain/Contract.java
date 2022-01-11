package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class Contract extends BaseClass implements Comparable<Contract> {
    private String contractNumber;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false)
    private Company company;
    private LocalDate initialDate;
    private LocalDate finalDate;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "contract_type_id", referencedColumnName = "contract_type_id", nullable = false)
    private ContractType contractType;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "contacts", joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "interested_id", referencedColumnName = "interested_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"contract_id", "interested_id"})})
    private List<Interested> interestedList = new ArrayList<>();


    @Override
    public int hashCode() {
        return Objects.hash(getId(), contractNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Contract)) {
            return false;
        }

        Contract mod = (Contract) obj;

        return Objects.equals(this.getId(), mod.getId())
                && Objects.equals(this.getContractNumber(), mod.getContractNumber());
    }

    @Override
    public int compareTo(Contract o) {
        String[] partsThis = contractNumber.split("/");
        String[] partsO = o.getContractNumber().split("/");

        try {
            /* Verifica o ano do contrato */
            if (Integer.parseInt(partsThis[1]) < Integer.parseInt(partsO[1]))
                return -1;

            if (Integer.parseInt(partsThis[1]) > Integer.parseInt(partsO[1]))
                return 1;

            /* Se o ano for o mesmo */
            if (Integer.valueOf(partsThis[1]) == Integer.valueOf(partsO[1])) {
                /* Verifica o número do contrato */

                if (Integer.parseInt(partsThis[0]) > Integer.parseInt(partsO[0]))
                    return 1;
                if (Integer.parseInt(partsThis[0]) < Integer.parseInt(partsO[0]))
                    return -1;
            }

            return 0;

        } catch (Exception e) {
            return 0;
        }

    }
}
