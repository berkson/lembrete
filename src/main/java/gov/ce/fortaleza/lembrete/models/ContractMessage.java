package gov.ce.fortaleza.lembrete.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by berkson
 * Date: 06/01/2022
 * Time: 20:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractMessage implements Message {

    private Map<String, String> data;


    /**
     * Retorna mensagem de alerta de expiração do contrato.
     * Antes do uso deve ser setado um Objeto MAP com
     * as chaves contractNumber e finalDate no construtor da classe
     *
     * @return String
     */
    @Override
    public String getText() {
        return String.format(getString(),
                data.get("contractNumber"), data.get("finalDate"));
    }

    private String getString() {
        return "<!DOCTYPE html>\n" + "<html>\n" + "    <head>\n"
                + "        <title>Informe do Sistema de Lembretes</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <style>\n" + "            a{text-decoration: none;}\n"
                + "            #obs{font-style: italic;}\n" + "        </style>\n" + "    </head>\n" + "    <body>\n"
                + "        <h1>Nova Notificação</h1>\n"
                + "        <p>O contrato Número:&nbsp;&nbsp;<strong>%s</strong></p> \n"
                + "        <p>ira expirar em %s.</p>\n"
                + "        <p>Este e um aviso automatico, favor nao responder.</p>\n" + "    </body>\n" + "</html>";
    }
}
