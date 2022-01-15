package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 23:11
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public boolean exists(String cpf) {
        return userRepository.existsByCpf(cpf);
    }
}
