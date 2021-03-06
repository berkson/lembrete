package gov.ce.fortaleza.lembrete.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ce.fortaleza.lembrete.domain.BaseClass;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import java.io.Serializable;
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
@Builder
public class UserDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
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

    // For Ldap Use
    private String username;
}
