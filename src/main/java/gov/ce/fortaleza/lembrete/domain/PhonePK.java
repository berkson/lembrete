package gov.ce.fortaleza.lembrete.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by berkson
 * Date: 30/11/2021
 * Time: 22:01
 */
@Getter
@Setter
public class PhonePK implements Serializable {

    private String tel;
    private Interested interested;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PhonePK))
            return false;
        PhonePK phonePK = (PhonePK) o;
        return Objects.equals(tel, phonePK.tel) &&
                Objects.equals(interested.getId(), phonePK.interested.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tel, interested.getId());
    }
}
