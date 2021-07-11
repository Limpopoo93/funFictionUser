package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.Role;
import FunFictionUserProject.funFictionUser.view.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto implements Serializable {
    private Long id;
    private String login;
    private String email;
    private String password;
    private String nameUser;
    private String surnameUser;
    private String background;
    private String language;
    private String status;
    private List<String> roles;

    public static UserListDto fromUser(User user) {
        UserListDto userDto = new UserListDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getSurnameUser());
        userDto.setNameUser(user.getNameUser());
        userDto.setSurnameUser(user.getSurnameUser());
        userDto.setBackground(user.getBackground());
        userDto.setLanguage(user.getLanguage());
        userDto.setStatus(String.valueOf(user.getStatus()));
        for (Role role: user.getRoles()){
            userDto.setRoles(Collections.singletonList(role.getRole()));
        }
        return userDto;
    }
}
