package FunFictionUserProject.funFictionUser.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.nio.file.Path;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel implements Serializable {
    private String fileName;
    private Path fileStorage;
}
