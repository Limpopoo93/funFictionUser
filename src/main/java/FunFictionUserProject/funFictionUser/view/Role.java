package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = "m_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_role")
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
