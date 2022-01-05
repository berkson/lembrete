package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by berkson
 * Date: 30/11/2021
 * Time: 21:29
 */
@Getter
@Setter
@Entity
@IdClass(PhonePK.class)
@Table(name = "phones")
@NoArgsConstructor
@AllArgsConstructor
public class Phone implements Serializable {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "interested_id",
            referencedColumnName = "interested_id",
            nullable = false)
    private Interested interested;

    @Id
    @Column(name = "tel", nullable = false)
    private String tel;
}
