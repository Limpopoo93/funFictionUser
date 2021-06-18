package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "m_fun_fiction")
public class FunFiction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_fun")
    private String nameFun;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "likes")
    private Integer like;
    @Column(name = "created")
    private Date created;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m_tags_fiction",
            joinColumns = {@JoinColumn(name = "id_fun_fiction", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_tags", referencedColumnName = "id")})
    private List<Tags> tags;

    @Override
    public String toString() {
        return "FunFiction{" +
                "id=" + id +
                ", nameFun='" + nameFun + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", rating=" + rating +
                ", like=" + like +
                ", created=" + created +
                ", genre=" + genre +
                ", tags=" + tags +
                '}';
    }
}
