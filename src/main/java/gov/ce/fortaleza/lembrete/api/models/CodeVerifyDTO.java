package gov.ce.fortaleza.lembrete.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

/**
 * Created by berkson
 * Date: 30/01/2022
 * Time: 19:21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeVerifyDTO {
    @CPF
    private String cpf;
    private String code;
    private String password;

    public CodeVerifyDTO(String cpf, String code) {
        this.cpf = cpf;
        this.code = code;
    }
}
