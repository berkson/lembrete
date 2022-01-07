package gov.ce.fortaleza.lembrete.domain;

import gov.ce.fortaleza.lembrete.enums.TimeCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:29
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "alerts", uniqueConstraints = {@UniqueConstraint(columnNames = {"time_cod", "time"})})
@AttributeOverride(name = "id", column = @Column(name = "alert_id"))
public class Alert extends BaseClass {

    @Builder
    public Alert(Long id, TimeCode timeCode, Integer time) {
        super(id);
        this.timeCode = timeCode;
        this.time = time;
    }

    public Alert(TimeCode timeCode, Integer time) {
        this.timeCode = timeCode;
        this.time = time;
    }

    @Column(name = "time_cod", columnDefinition = "bpchar(1) NOT NULL")
    @Enumerated(EnumType.STRING)
    private TimeCode timeCode;
    private Integer time;
    @ManyToMany
    @JoinTable(name = "types_alerts",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_type_id"))
    private List<ContractType> contractTypes = new ArrayList<>();
    @Transient
    private String cron;
}
