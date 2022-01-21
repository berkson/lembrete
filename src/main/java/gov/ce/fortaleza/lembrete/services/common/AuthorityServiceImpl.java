package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.mappers.AuthorityMapper;
import gov.ce.fortaleza.lembrete.api.models.AuthorityDTO;
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
    private final AuthorityMapper authorityMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository,
                                AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public AuthorityDTO save(AuthorityDTO entity) {
        Authority savedAuthority = authorityRepository
                .save(authorityMapper.authorityDTOToAuthority(entity));

        return authorityMapper.authorityToAuthorityDTO(savedAuthority);
    }

    @Override
    @Transactional
    public Optional<AuthorityDTO> findById(String s) {

        return Optional.ofNullable(authorityMapper
                .authorityToAuthorityDTO(authorityRepository.getById(s)));
    }

    @Override
    public List<AuthorityDTO> findAll() {
        return authorityRepository.findAll()
                .stream().map(authorityMapper::authorityToAuthorityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return authorityRepository.count();
    }

    @Override
    @Transactional
    public List<AuthorityDTO> findAllByUserId(long id) {
        return StreamSupport.stream(authorityRepository
                        .findAuthoritiesByUserId(id).spliterator(), false)
                .map(authorityMapper::authorityToAuthorityDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<AuthorityDTO> saveAll(Iterable<AuthorityDTO> authorities) {
        List<Authority> authorityList = StreamSupport.stream(authorities.spliterator(), false)
                .map(authorityMapper::authorityDTOToAuthority).collect(Collectors.toList());

        return authorityRepository.saveAll(authorityList)
                .stream().map(authorityMapper::authorityToAuthorityDTO)
                .collect(Collectors.toList());
    }
}
