package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.TAGS_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.TAGS;
import static FunFictionUserProject.funFictionUser.util.DataConstant.TYPE_TAGS;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"funFictions"})
@Entity
@Table(name = TAGS_TABLE)
public class Tags implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = TYPE_TAGS)
    private String typeTags;
    @ManyToMany(mappedBy = TAGS)
    private List<FunFiction> funFictions;

}
