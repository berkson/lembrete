package gov.ce.fortaleza.lembrete.enums;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 21:08
 */
public enum ContractTypes {
    AQUISICAO_BENS("aquisição de bens"),
    SERVICO_CONTINUADO("serviços de prestação continuada"),
    EXCEPCIONAL("contrato excepcional"),
    EMERGENCIAL("contrato emergencial"),
    CONVENIO("convênio"),
    COOP("acordo de cooperação"),
    CREDENCIA("termos de credenciamento");

    private String type;

    ContractTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
