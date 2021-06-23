package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String secondPassword;

    public User toUser() {
        User user = new User();
        user.setLogin(username);
        user.setNameUser(firstName);
        user.setSurnameUser(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static RegisterRequestDto fromUser(User user) {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername(user.getLogin());
        registerRequestDto.setFirstName(user.getNameUser());
        registerRequestDto.setLastName(user.getSurnameUser());
        registerRequestDto.setEmail(user.getEmail());
        registerRequestDto.setPassword(user.getPassword());
        return registerRequestDto;
    }
}
