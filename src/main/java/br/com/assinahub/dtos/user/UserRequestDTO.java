package br.com.assinahub.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank
        @Size(max = 150)
        String name,

        @NotBlank 
        @Email
        @Size
        (max = 255) String email,

        @NotBlank
        @Size
        (min = 8, max = 72) String password,

        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "telefone deve ter de 10 a 15 dígitos")
        String phone
) {
}
