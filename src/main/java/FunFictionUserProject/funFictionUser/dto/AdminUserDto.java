package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.Status;
import FunFictionUserProject.funFictionUser.view.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(username);
        user.setNameUser(firstName);
        user.setSurnameUser(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getLogin());
        adminUserDto.setFirstName(user.getNameUser());
        adminUserDto.setLastName(user.getSurnameUser());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(user.getStatus().name());
        return adminUserDto;
    }
}
