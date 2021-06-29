package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.COMMENT_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = COMMENT_TABLE)
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = TEXT_COMMENT)
    private String textComment;
    @Column(name = CREATED)
    private Date created;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = ID_USER, nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = ID_FUN_FIC, nullable = false)
    private FunFiction funFiction;
}
