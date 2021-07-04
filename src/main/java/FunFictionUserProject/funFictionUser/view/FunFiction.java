package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.FUN_FIC_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"genre", "tags", "user"})
@Entity
@Table(name = FUN_FIC_TABLE)
public class FunFiction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = NAME_FUN)
    private String nameFun;
    @Column(name = SHORT_DESCRIPTION)
    private String shortDescription;
    @Column(name = RATING)
    private Double rating;
    @Column(name = LIKES)
    private Integer like;
    @Column(name = CREATED)
    private Date created;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = ID_GENRE, nullable = false)
    private Genre genre;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = TAGS_FUNCTION,
            joinColumns = {@JoinColumn(name = ID_FUN_FIC, referencedColumnName = ID)},
            inverseJoinColumns = {@JoinColumn(name = ID_TAGS, referencedColumnName = ID)})
    private List<Tags> tags;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = ID_USER, nullable = false)
    private User user;

}
