package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.mappers.AuthorityMapper;
import gov.ce.fortaleza.lembrete.api.mappers.UserMapper;
import gov.ce.fortaleza.lembrete.api.models.CodeVerifyDTO;
import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.exceptions.InvalidRecoveryCodeException;
import gov.ce.fortaleza.lembrete.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
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

    @Override
    public Map<String, Boolean> verifyCode(CodeVerifyDTO codeVerifyDTO)
            throws InvalidRecoveryCodeException {
        User user = Optional.ofNullable(this.userRepository.findByCpf(codeVerifyDTO.getCpf()))
                .orElseThrow(EntityNotFoundException::new);
        if (user.getRecoveryCode() != null) {
            return Map.of("isValid", user.getRecoveryCode()
                    .equals(codeVerifyDTO.getCode()));
        } else {
            throw new InvalidRecoveryCodeException("gen.new.code");
        }
    }

    @Override
    @Transactional
    public void changePassword(CodeVerifyDTO codeVerifyDTO) {
        User user = userRepository.findByCpf(codeVerifyDTO.getCpf());
        String encodedPass = BCrypt.hashpw(codeVerifyDTO.getPassword(), BCrypt.gensalt());
        user.setPassword(encodedPass);
        user.setRecoveryCode(null);
        this.save(user);
    }
}
