package gov.ce.fortaleza.lembrete.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
public class ContractMessage implements Message, Serializable {

    private Map<String, String> data;


    /**
     * Retorna mensagem de alerta de expiração do contrato.
     * Antes do uso deve ser setado um Objeto MAP com
     * as chaves contractNumber e finalDate no construtor da classe
     *
     * @return String
     */
    @Override
    public String generateText() {
        return String.format(getString(),
                data.get("contractNumber"), data.get("finalDate"));
    }

    private String getString() {
        char quotes = '"';
        return "<!DOCTYPE html>\n" + "<html>\n" + "    <head>\n"
                + "        <title>Informe do Sistema de Lembretes</title>\n"
                + "        <meta charset=" + quotes + "UTF-8" + quotes + ">\n"
                + "        <meta name=" + quotes + "viewport" + quotes + "content="
                + quotes + "width=device-width, initial-scale=1.0" + quotes + ">\n"
                + "        <style>\n" + "            a{text-decoration: none;}\n"
                + "            #obs{font-style: italic;}\n" + "        </style>\n" + "    </head>\n" + "    <body>\n"
                + "        <h1>Nova Notificação</h1>\n"
                + "        <p>O contrato Número:&nbsp;&nbsp;<strong>%s</strong></p> \n"
                + "        <p>irá expirar em %s.</p>\n"
                + "        <p>Este é um aviso automático, favor não responder a este email.</p>\n" + "    </body>\n" + "</html>";
    }
}
