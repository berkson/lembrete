package gov.ce.fortaleza.lembrete.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Created by berkson
 * Date: 20/01/2022
 * Time: 18:26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDTO {
    @Length(min = 6, max = 100)
    @Pattern(regexp = "^(ROLE_)[A-Z_]+$")
    private String authority;
    @Length(min = 5, max = 255)
    private String description;
}
