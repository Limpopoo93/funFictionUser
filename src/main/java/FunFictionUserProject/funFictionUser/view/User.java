package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "m_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name_user")
    private String nameUser;
    @Column(name = "surname_user")
    private String surnameUser;
    @Column(name = "status")
    private Status status;
    @Column(name = "created")
    private Date created;
    @Column(name = "updated")
    private Date updated;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m_user_role",
            joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id")})
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nameUser='" + nameUser + '\'' +
                ", surnameUser='" + surnameUser + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", updated=" + updated +
                ", roles=" + roles +
                '}';
    }
}
