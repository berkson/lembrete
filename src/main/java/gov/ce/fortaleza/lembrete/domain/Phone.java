package gov.ce.fortaleza.lembrete.domain;

import lombok.Getter;
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
public class Phone implements Serializable {
    @Id
    private String tel;

    @Id
    @ManyToOne
    @JoinColumn(name = "interested_id", referencedColumnName = "interested_id")
    private Interested interested;

}
