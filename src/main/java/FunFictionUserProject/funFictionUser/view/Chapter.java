package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"funFiction"})
@Entity
@Table(name = "m_chapter")
//Главы
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_chapter")
    private int numberChapter;
    @Column(name = "name_chapter")
    private String nameChapter;
    @Column(name = "text_chapter")
    private String textChapter;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_fun_fiction", nullable = false)
    private FunFiction funFiction;
}
