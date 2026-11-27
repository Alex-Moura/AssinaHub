package br.com.assinahub.services;

import br.com.assinahub.dtos.user.UserRequestDTO;
import br.com.assinahub.dtos.user.UserResponseDTO;
import br.com.assinahub.dtos.user.UserUpdateDTO;

import java.util.UUID;

public interface UserService {
    UserResponseDTO create(UserRequestDTO request);

    UserResponseDTO findById(UUID userId);

    UserResponseDTO update(UUID userId, UserUpdateDTO request);

    void delete(UUID userId);
}
