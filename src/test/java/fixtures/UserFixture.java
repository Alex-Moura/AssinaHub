package fixtures;

import br.com.assinahub.dtos.user.UserRequestDTO;
import br.com.assinahub.dtos.user.UserResponseDTO;
import br.com.assinahub.entities.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public final class UserFixture {

    public static final UUID USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final LocalDateTime CREATED_AT = LocalDateTime.of(2026, 1, 1, 10, 0);
    public static final String NAME = "AlexMoura";
    public static final String EMAIL = "alex@gmail.com";
    public static final String PASSWORD = "123@321a";
    public static final String PHONE = "9999999999";

    private UserFixture() {
    }

    public static UserRequestDTO userRequest(){
        return userRequest(EMAIL, PHONE);
    }

    public static UserRequestDTO userRequest(String email, String phone){
        return new UserRequestDTO(
            NAME,
            email,
            PASSWORD,
            phone
        );
    }

    public static UserResponseDTO userResponse(){
        return new UserResponseDTO(
            USER_ID,
            NAME,
            EMAIL,
            PHONE,
            CREATED_AT,
            null
        );
    }

    public static User userEntity(){
        return userEntity(EMAIL, PHONE);
    }


    public static User userEntity(String email, String phone) {
        User user = new User(
            NAME,
            email,
            PASSWORD,
            phone
        );

        ReflectionTestUtils.setField(user, "id", USER_ID);
        ReflectionTestUtils.setField(user, "createdAt", CREATED_AT);
        ReflectionTestUtils.setField(user, "updatedAt", null);

        return user;
    }
}
