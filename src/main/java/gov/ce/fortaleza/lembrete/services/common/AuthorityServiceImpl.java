package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.domain.Authority;
import gov.ce.fortaleza.lembrete.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 22:57
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional
    public Authority save(Authority entity) {
        return authorityRepository.save(entity);
    }

    @Override
    public Optional<Authority> findById(String s) {
        return Optional.ofNullable(authorityRepository.getById(s));
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public long count() {
        return authorityRepository.count();
    }

    @Override
    public List<Authority> findAllByUserId(long id) {
        return StreamSupport.stream(authorityRepository
                        .findAuthoritiesByUserId(id).spliterator(), false)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<Authority> saveAll(Iterable<Authority> authorities) {
        return authorityRepository.saveAll(authorities);
    }
}
