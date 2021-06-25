package FunFictionUserProject.funFictionUser.security;

import FunFictionUserProject.funFictionUser.view.Role;
import FunFictionUserProject.funFictionUser.view.Status;
import FunFictionUserProject.funFictionUser.view.User;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/*
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getNameUser(),
                user.getSurnameUser(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getRole())
                ).collect(Collectors.toList());
    }
}


 */