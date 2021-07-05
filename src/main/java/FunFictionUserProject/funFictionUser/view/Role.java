package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.ROLE_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"users"})
@Entity
@Table(name = ROLE_TABLE)
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = TYPE_ROLE)
    private String role;
    @ManyToMany(mappedBy = ROLES, fetch = FetchType.LAZY)
    private List<User> users;

}
