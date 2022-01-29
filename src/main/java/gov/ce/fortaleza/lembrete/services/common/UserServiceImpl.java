package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.mappers.AuthorityMapper;
import gov.ce.fortaleza.lembrete.api.mappers.UserMapper;
import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 23:11
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthorityService authorityService;
    private final AuthorityMapper authorityMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           AuthorityService authorityService, AuthorityMapper authorityMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authorityService = authorityService;
        this.authorityMapper = authorityMapper;
    }

    @Override
    @Transactional
    public UserDTO save(UserDTO entity) {
        User savedUser = userRepository.save(userMapper.userDTOToUser(entity));
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(userMapper::userToUserDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO findByCpf(String cpf) {
        UserDTO userDTO = userMapper.userToUserDTO(userRepository.findByCpf(cpf));
        userDTO.setAuthorities(authorityService.findAllByUserId(userDTO.getId()));
        return userDTO;
    }

    @Override
    @Transactional
    public User findByUsername(String cpf) {
        User user = userRepository.findByCpf(cpf);
        user.setAuthorities(authorityService.findAllByUserId(user.getId())
                .stream().map(authorityMapper::authorityDTOToAuthority)
                .collect(Collectors.toList()));
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
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
