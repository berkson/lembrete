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
 * Date: 29/11/2021
 * Time: 23:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reminders")
@AttributeOverride(name = "id", column = @Column(name = "reminder_id"))
@Entity
public class Reminder extends BaseClass {
    private String contractNumber;
    @OneToOne
    private Company company;
    @OneToOne
    @JoinColumn(name = "contract_type_id", referencedColumnName = "contract_type_id")
    private ContractType contractType;
    private LocalDate initialDate;
    private LocalDate extendedDate;
    @ManyToMany
    @JoinTable(name = "contacts", joinColumns = @JoinColumn(name = "reminder_id"),
            inverseJoinColumns = @JoinColumn(name = "interested_id"))
    private List<Interested> interesteds = new ArrayList<>();
}
