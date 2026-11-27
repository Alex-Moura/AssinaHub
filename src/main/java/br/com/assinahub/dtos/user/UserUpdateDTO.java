package br.com.assinahub.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @Size(max = 150)
        String name,

        @Email
        @Size
        (max = 255) String email,
        
        @Pattern(
            regexp = "^(?:\\s*\\d){10,15}\\s*$",
            message = "telefone deve ter de 10 a 15 dígitos (somente números)"
        )
        @Size(max = 30)
        String phone
) {
}
