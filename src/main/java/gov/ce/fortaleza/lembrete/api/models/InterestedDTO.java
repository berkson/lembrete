package gov.ce.fortaleza.lembrete.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 21:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestedDTO {
    private static final long serialVersionUID = -2811300893110523169L;
    private String cpf;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
}
