package gov.ce.fortaleza.lembrete.repositories;

/**
 * Created by berkson
 * Date: 18/04/2022
 * Time: 23:54
 */
public class ContractRepositoryImpl {
    private String query = "SELECT * FROM contracts ORDER BY substring(contract_number  from '\\d+$'), \n" +
            "substring(contract_number from '^\\d+')\\:\\:int ?3 LIMIT ?2 OFFSET ?1";

    //TODO: implement and return a page here. refactor the methods.
    //List<Contract> findAllByContractNumber(int offset, int quantityPerPage, String dir);
}
