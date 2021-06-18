package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "m_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_role")
    private String role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", users=" + users +
                '}';
    }
}
