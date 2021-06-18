package FunFictionUserProject.funFictionUser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {
    private String login;
    private String password;
}
