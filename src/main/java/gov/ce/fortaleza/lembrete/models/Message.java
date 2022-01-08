package gov.ce.fortaleza.lembrete.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Created by berkson
 * Date: 06/01/2022
 * Time: 20:02
 */
@JsonDeserialize(as = ContractMessage.class)
public interface Message extends Serializable {
    String generateText();
}
