package FunFictionUserProject.funFictionUser.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.USER_ROLE;
import static FunFictionUserProject.funFictionUser.util.DataBaseConstant.USER_TABLE;
import static FunFictionUserProject.funFictionUser.util.DataConstant.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"roles"})
@Entity
@Table(name = USER_TABLE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = LOGIN)
    private String login;
    @Column(name = EMAIL)
    private String email;
    @Column(name = PASSWORD)
    private String password;
    @Column(name = NAME_USERS)
    private String nameUser;
    @Column(name = SURNAME_USER)
    private String surnameUser;
    @Column(name = BACKGROUND)
    private String background;
    @Column(name = LANGUAGE)
    private String language;
    @Column(name = STATUS)
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Column(name = CREATED)
    private Date created;
    @Column(name = UPDATED)
    private Date updated;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = USER_ROLE,
            joinColumns = {@JoinColumn(name = ID_USER, referencedColumnName = ID)},
            inverseJoinColumns = {@JoinColumn(name = ID_ROLE, referencedColumnName = ID)})
    private List<Role> roles;

}
