package br.com.assinahub.controllers;

import br.com.assinahub.dtos.user.UserRequestDTO;
import br.com.assinahub.dtos.user.UserResponseDTO;
import br.com.assinahub.dtos.user.UserUpdateDTO;
import br.com.assinahub.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO requestDTO){
        UserResponseDTO response = userService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id){
        UserResponseDTO response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id,
                                                  @RequestBody @Valid UserUpdateDTO requestDTO){
        UserResponseDTO response = userService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }
}
