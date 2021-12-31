package gov.ce.fortaleza.lembrete.enums;

public enum EmailPriority {
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
