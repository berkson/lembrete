package gov.ce.fortaleza.lembrete.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by berkson
 * Date: 28/01/2022
 * Time: 18:41
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordMessage implements Message {

    private Map<String, String> data;
    private String type;

    public RecoverPasswordMessage(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public String generateText() {
        return getString(data.get("code"));
    }

    //TODO: mudar o endereço do site para mudança de senha
    private String getString(String code) {
        return String.format("<!DOCTYPE html>\n" + "<html>\n" + "    <head>\n"
                + "        <title>Recuperação de Senha</title>\n" + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <style>\n" + "            a{text-decoration: none;}\n"
                + "            #obs{font-style: italic;}\n" + "        </style>\n" + "    </head>\n" + "    <body>\n"
                + "        <h1>Recuperação de Senha</h1>\n" + "        <p>Gentileza informar Cpf e<p>\n"
                + "        <p>Código:&nbsp;&nbsp;<strong>%s</strong></p> \n"
                + "        <p>na página: <a href=\"https://scc.centralamc.com.br/login/informarcodigo.xhtml \">Verificação do Código</a></p>\n"
                + "        <p id=\"obs\">Caso não tenha solicitado a recuperação de senha, gentileza informar ao administrador do sistema</p>\n"
                + "    </body>\n" + "</html>", code);
    }
}
