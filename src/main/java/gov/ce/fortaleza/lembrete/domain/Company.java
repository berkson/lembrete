package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "companies", uniqueConstraints = @UniqueConstraint(columnNames = {"cnpj"}))
@AttributeOverride(name = "id", column = @Column(name = "company_id"))
public class Company extends BaseClass {
    @Column(unique = true, length = 14)
    private String cnpj;
    private String name;
}
