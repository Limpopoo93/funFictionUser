package FunFictionUserProject.funFictionUser.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrors {
    private String message;
    private List<String> details;
    private HttpStatus status;
    private LocalDateTime localDateTime;
}
