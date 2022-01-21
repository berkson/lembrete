package gov.ce.fortaleza.lembrete.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * Created by berkson
 * Date: 20/01/2022
 * Time: 18:11
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseClass {
    @CPF
    private String cpf;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Email
    private String email;
    @Length(min = 3, max = 255)
    private String name;
    private boolean enabled;
    @JsonProperty(value = "roles")
    private List<AuthorityDTO> authorities;
}
