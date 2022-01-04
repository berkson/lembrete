package gov.ce.fortaleza.lembrete.api.models;

import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
public class InterestedDTO extends BaseClass {
    private static final long serialVersionUID = -2811300893110523169L;
    @CPF
    private String cpf;
    @Length(min = 3, max = 255)
    private String name;
    @Email
    private String email;
    @NotEmpty
    private List<PhoneDTO> phones;
}
