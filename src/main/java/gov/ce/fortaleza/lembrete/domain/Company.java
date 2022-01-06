package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by berkson
 * Date: 30/11/2021
 * Time: 20:57
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cnpj"}))
@AttributeOverride(name = "id", column = @Column(name = "company_id"))
public class Company extends BaseClass {
    @Column(unique = true, length = 14)
    private String cnpj;
    private String name;
    @OneToMany(mappedBy = "company")
    private List<Contract> contracts = new ArrayList<>();

    public void addContract(Contract contract) {
        this.verifyContracts();
        this.contracts.add(contract);
    }

    private void verifyContracts() {
        if (contracts == null)
            contracts = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(this.getId(), company.getId()) && Objects.equals(cnpj, company.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), cnpj);
    }
}
