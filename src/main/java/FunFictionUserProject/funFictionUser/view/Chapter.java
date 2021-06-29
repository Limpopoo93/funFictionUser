package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.CHAPTER_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = CHAPTER_TABLE)
//Главы
public class Chapter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = NUMBER_CHAPTER)
    private int numberChapter;
    @Column(name = NAME_CHAPTER)
    private String nameChapter;
    @Column(name = TEXT_CHAPTER)
    private String textChapter;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = ID_FUN_FIC, nullable = false)
    private FunFiction funFiction;
}
