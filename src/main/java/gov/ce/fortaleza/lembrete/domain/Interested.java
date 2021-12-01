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
 * Time: 21:00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interesteds")
@AttributeOverride(name = "id", column = @Column(name = "interested_id"))
@Entity
public class Interested extends BaseClass {
    @Column(unique = true, length = 11)
    private String cpf;
    private String name;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interested")
    private List<Phone> phones;
    @ManyToMany(mappedBy = "interesteds")
    private List<Reminder> reminders;
}
