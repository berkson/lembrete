package gov.ce.fortaleza.lembrete.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 21:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO implements Serializable {
    private static final long serialVersionUID = -2958182187925286191L;
    private String tel;
}
