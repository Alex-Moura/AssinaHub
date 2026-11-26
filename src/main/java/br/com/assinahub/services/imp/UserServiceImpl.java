package br.com.assinahub.services.imp;

import br.com.assinahub.dtos.user.UserRequestDTO;
import br.com.assinahub.dtos.user.UserResponseDTO;
import br.com.assinahub.entities.User;
import br.com.assinahub.exceptions.ConflictException;
import br.com.assinahub.exceptions.ResourceNotFoundException;
import br.com.assinahub.mappers.UserMapper;
import br.com.assinahub.repositories.UserRepository;
import br.com.assinahub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO requestDTO) {

        String email = requestDTO.email().trim().toLowerCase();
        String phone = requestDTO.phone().replaceAll("\\s+", "");

        if (userRepository.existsByEmail(email)){
            throw new ConflictException("Email já cadastrado");
        }
        if (userRepository.existsByPhone(phone)) {
            throw new ConflictException("Telefone já cadastrado");
        }
        User user = userMapper.toEntity(requestDTO);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO findById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO update(UUID userId, UserRequestDTO request) {
        return null;
    }

    @Override
    public void delete(UUID userId) {

    }
}
