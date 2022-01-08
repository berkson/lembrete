package gov.ce.fortaleza.lembrete.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * Created by berkson
 * Date: 06/01/2022
 * Time: 20:02
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = ContractMessage.class, name = "contract_message")})
public interface Message extends Serializable {

    /**
     * Retorna mensagem a ser inclusa no correio eletrônico..
     * Antes do uso deve ser setado um Objeto MAP com
     * os dados e uma String, no construtor da classe,
     * esta especificando a implementação da ‘interface’
     * Message e aqueles dados referentes a implementação.
     *
     * @return String Texto com a mensagem do correio eletrônico
     */
    String generateText();
}
