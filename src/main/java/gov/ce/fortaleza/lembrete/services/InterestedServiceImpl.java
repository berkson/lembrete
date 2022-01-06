package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.mappers.InterestedMapper;
import gov.ce.fortaleza.lembrete.api.models.InterestedDTO;
import gov.ce.fortaleza.lembrete.domain.Interested;
import gov.ce.fortaleza.lembrete.repositories.InterestedRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 05/01/2022
 * Time: 23:27
 */
@Service
public class InterestedServiceImpl implements InterestedService {

    private final InterestedRepository interestedRepository;
    private final InterestedMapper interestedMapper;

    public InterestedServiceImpl(InterestedRepository interestedRepository,
                                 InterestedMapper interestedMapper) {
        this.interestedRepository = interestedRepository;
        this.interestedMapper = interestedMapper;
    }

    @Override
    @Transactional
    public InterestedDTO save(InterestedDTO entity) {
        Interested interested = interestedMapper.interestedDTOToInterested(entity);
        interested.addInterestedToPhones(); // evitar quebrar a chave composta
        interestedRepository.save(interested);

        return interestedMapper.interestedToInterestedDTO(interested);
    }

    @Override
    public Optional<InterestedDTO> findById(Long id) {
        return Optional.ofNullable(
                interestedMapper.interestedToInterestedDTO(
                        interestedRepository.getById(id)
                )
        );
    }

    @Override
    public List<InterestedDTO> findAll() {
        return interestedRepository.findAll()
                .stream().map(interestedMapper::interestedToInterestedDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<InterestedDTO> findByCpf(String cpf) {
        return Optional.ofNullable(
                interestedMapper.interestedToInterestedDTO(
                        interestedRepository.getByCpf(cpf)
                )
        );
    }
}
