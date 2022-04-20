package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by berkson
 * Date: 18/04/2022
 * Time: 23:54
 */
@Repository
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public ContractRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Page<Contract> findAllByContractNumber(PageRequest pageRequest, String dir) {
        final String sql = "SELECT * FROM contracts ORDER BY substring(contract_number  from '\\d+$'), " +
                "substring(contract_number from '^\\d+')\\:\\:int " + dir + " LIMIT " +
                pageRequest.getPageSize() + " OFFSET " + pageRequest.getOffset();
        Query query = entityManager.createNativeQuery(sql, Contract.class);
        List<Contract> contracts = query.getResultList();
        return new PageImpl<>(contracts, pageRequest, this.contractsTotal());
    }

    private Long contractsTotal() {
        final String sql = "SELECT count(*) FROM contracts";
        Object obj = entityManager.createNativeQuery(sql).getSingleResult();
        return Optional.ofNullable(((BigInteger) obj).longValue()).orElse(0L);
    }
}
