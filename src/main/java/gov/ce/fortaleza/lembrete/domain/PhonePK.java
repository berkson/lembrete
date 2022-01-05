package gov.ce.fortaleza.lembrete.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by berkson
 * Date: 05/01/2022
 * Time: 00:18
 */
@Getter
@Setter
public class PhonePK implements Serializable {

    private Interested interested;
    private String tel;


    public PhonePK() {
    }

    public PhonePK(String tel, Interested interested) {
        this.tel = tel;
        this.interested = interested;
    }

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
