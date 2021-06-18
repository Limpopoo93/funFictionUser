package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(username);
        user.setNameUser(firstName);
        user.setSurnameUser(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getLogin());
        userDto.setFirstName(user.getNameUser());
        userDto.setLastName(user.getSurnameUser());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
