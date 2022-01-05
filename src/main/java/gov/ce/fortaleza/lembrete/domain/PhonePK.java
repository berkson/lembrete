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

    private Long interested;
    private String tel;


    public PhonePK() {
    }

    public PhonePK(Long interested, String tel) {
        this.interested = interested;
        this.tel = tel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhonePK phonePK = (PhonePK) o;
        return Objects.equals(interested, phonePK.interested) && Objects.equals(tel, phonePK.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interested, tel);
    }
}
