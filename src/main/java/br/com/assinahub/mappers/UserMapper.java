package br.com.assinahub.mappers;

import br.com.assinahub.dtos.user.UserRequestDTO;
import br.com.assinahub.dtos.user.UserResponseDTO;
import br.com.assinahub.dtos.user.UserUpdateDTO;
import br.com.assinahub.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDTO(User user);
    User toEntity(UserRequestDTO requestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(UserUpdateDTO requestDTO, @MappingTarget User user);
}
