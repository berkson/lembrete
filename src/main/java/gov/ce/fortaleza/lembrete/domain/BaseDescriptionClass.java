package gov.ce.fortaleza.lembrete.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:30
 */
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BaseDescriptionClass extends BaseClass {
    private String description;

    public BaseDescriptionClass(Long id, String description) {
        super(id);
        this.description = description;
    }

    public BaseDescriptionClass(String description) {
        this.description = description;
    }
}
