package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.GENRE_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.DESCRIPTION;
import static FunFictionUserProject.funFictionUser.util.DataConstant.TYPE_GENRE;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = GENRE_TABLE)
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = TYPE_GENRE)
    private String typeGenre;
    @Column(name = DESCRIPTION)
    private String description;
}
