package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"funFictions"})
@Entity
@Table(name = "m_tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_tags")
    private String typeTags;
    @ManyToMany(mappedBy = "tags")
    private List<FunFiction> funFictions;

}
