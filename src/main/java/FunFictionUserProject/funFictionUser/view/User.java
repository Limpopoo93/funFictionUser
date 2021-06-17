package FunFictionUserProject.funFictionUser.view;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = "m_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "new_year")
    private Date newYear;
    @Column(name = "status")
    private Status status;
    @Column(name = "created")
    private Date created;
    @Column(name = "updated")
    private Date updated;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_user_role",
            joinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")})
    private List<Role> roles;
}
