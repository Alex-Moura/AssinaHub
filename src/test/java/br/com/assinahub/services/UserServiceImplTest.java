package br.com.assinahub.services;

import br.com.assinahub.dtos.user.UserRequestDTO;
import br.com.assinahub.dtos.user.UserResponseDTO;
import br.com.assinahub.entities.User;
import br.com.assinahub.exceptions.ConflictException;
import br.com.assinahub.mappers.UserMapper;
import br.com.assinahub.repositories.UserRepository;
import br.com.assinahub.services.imp.UserServiceImpl;
import fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl")
public class UserServiceImplTest {

    private static final String HASHED_PASSWORD = "hash-bcrypt";


    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    @DisplayName("create")
    class create {

        @Captor
        private ArgumentCaptor<User> userCaptor;

        @ParameterizedTest(name = "e-mail [{0}] should be saved as [{1}]")
        @CsvSource(delimiter = '|', value = {
                "'  ALEX@Email.com ' | alex@email.com",
                "ALEX@EMAIL.COM      | alex@email.com",
                "'alex@email.com  '  | alex@email.com"
        })
        @DisplayName("should normalize email (trim + lowercase) before checking and saving")
        void shouldNormalizeEmailBeforeCheckingAndSaving(String rawEmail, String expectedEmail) {
            UserRequestDTO request = UserFixture.userRequest(rawEmail, UserFixture.PHONE);
            User user = UserFixture.userEntity(rawEmail, UserFixture.PHONE);

            when(userMapper.toEntity(request)).thenReturn(user);
            when(passwordEncoder.encode(any())).thenReturn(HASHED_PASSWORD);
            when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

            userService.create(request);

            verify(userRepository).existsByEmail(expectedEmail);
            verify(userRepository).save(userCaptor.capture());
            assertThat(userCaptor.getValue().getEmail()).isEqualTo(expectedEmail);
        }

        @ParameterizedTest(name = "phone [{0}] should be saved as [{1}]")
        @CsvSource(delimiter = '|', value = {
                "'  9999999999 '   | 9999999999",
                "'11 99999 9999'   | 11999999999",
                "'  11 99999 9999 ' | 11999999999"
        })
        @DisplayName("should remove all whitespace from phone before checking and saving")
        void shouldRemoveWhitespaceFromPhoneBeforeCheckingAndSaving(String rawPhone, String expectedPhone) {
            UserRequestDTO request = UserFixture.userRequest(UserFixture.EMAIL, rawPhone);
            User user = UserFixture.userEntity(UserFixture.EMAIL, rawPhone);

            when(userMapper.toEntity(request)).thenReturn(user);
            when(passwordEncoder.encode(any())).thenReturn(HASHED_PASSWORD);
            when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

            userService.create(request);

            verify(userRepository).existsByPhone(expectedPhone);
            verify(userRepository).save(userCaptor.capture());
            assertThat(userCaptor.getValue().getPhone()).isEqualTo(expectedPhone);
        }

        @Test
        @DisplayName("Should create a user and encoding the password")
        void shouldCreateUserSuccessfully(){
            UserRequestDTO request = UserFixture.userRequest();
            User user = UserFixture.userEntity();
            UserResponseDTO response = UserFixture.userResponse();

            when(userRepository.existsByEmail(request.email())).thenReturn(false);
            when(userRepository.existsByPhone(request.phone())).thenReturn(false);
            when(userMapper.toEntity(request)).thenReturn(user);
            when(passwordEncoder.encode(request.password())).thenReturn(HASHED_PASSWORD);
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(userMapper.toResponseDTO(user)).thenReturn(response);

            UserResponseDTO result = userService.create(request);

            verify(userRepository).save(userCaptor.capture());
            User userPersisted = userCaptor.getValue();

            assertThat(userPersisted.getEmail()).isEqualTo("alex@gmail.com");
            assertThat(userPersisted.getPhone()).isEqualTo("9999999999");
            assertThat(userPersisted.getPassword())
                    .isEqualTo(HASHED_PASSWORD)
                    .isNotEqualTo("123@321a");
            assertThat(result).isEqualTo(response);
        }

        @Test
        @DisplayName("should throw ConflictException when the email already exists")
        public void shouldThrowWhenEmailAlreadyExists(){
            UserRequestDTO request = UserFixture.userRequest("  ALEX@Gmail.com ", UserFixture.PHONE);

            when(userRepository.existsByEmail("alex@gmail.com")).thenReturn(true);

            assertThatThrownBy(() ->userService.create(request))
                    .isInstanceOf(ConflictException.class)
                    .hasMessage("Email já cadastrado");

            verify(userRepository, never()).existsByPhone(anyString());
            verify(userRepository, never()).save(any());
            verifyNoInteractions(userMapper, passwordEncoder);
        }

        @Test
        @DisplayName("Should throw ConflictException when the phone already exists")
        public void shouldThrowWhenPhoneAlreadyExists(){
            UserRequestDTO request = UserFixture.userRequest(UserFixture.EMAIL, " 11 99999 9999 ");

            when(userRepository.existsByEmail(UserFixture.EMAIL)).thenReturn(false);
            when(userRepository.existsByPhone("11999999999")).thenReturn(true);

            assertThatThrownBy(()-> userService.create(request))
                    .isInstanceOf(ConflictException.class)
                    .hasMessage("Telefone já cadastrado");

            verify(userRepository, never()).save(any());
            verifyNoInteractions(userMapper, passwordEncoder);
        }
    }
}
