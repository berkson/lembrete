package gov.ce.fortaleza.lembrete.domain;

import gov.ce.fortaleza.lembrete.enums.TimeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long id;
    @Column(name = "time_cod", columnDefinition = "CHAR NOT NULL")
    @Enumerated(EnumType.STRING)
    private TimeCode timeCode;
    private Integer time;
}
