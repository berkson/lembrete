package gov.ce.fortaleza.lembrete.enums;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 21:08
 */
public enum ContractTypes {
    AQUISICAO_BENS("aquisição de bens", 12, "AB"),
    SERVICO_CONTINUADO("serviços de prestação continuada", 60, "SC"),
    EXCEPCIONAL("contrato excepcional", 12, "EX"),
    EMERGENCIAL("contrato emergencial", 6, "EM"),
    CONVENIO("convênio", 60, "CON"),
    COOP("acordo de cooperação", 60, "COO"),
    CREDENCIA("termos de credenciamento", 60, "CRE");

    private final String description;
    private final int validity;
    private final String code;

    ContractTypes(String description, int validity, String code) {
        this.description = description;
        this.validity = validity;
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public int getValidity() {
        return this.validity;
    }

    public String getCode() {
        return code;
    }
}
