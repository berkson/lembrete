package gov.ce.fortaleza.lembrete.enums;

import java.io.Serializable;

public enum EmailPriority implements Serializable {
	HIGH("1"),
	NORMAL("3"),
	LOW("5");

	private String prioridade;

	private EmailPriority(String tipoPrioridade) {
		prioridade = tipoPrioridade;
	}
	
	public String getPrioridade() {
		return prioridade;
	}
}
