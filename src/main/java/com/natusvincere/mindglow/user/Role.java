package com.natusvincere.mindglow.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.natusvincere.mindglow.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

    STUDENT(Collections.emptySet()),
    TEACHER(
            Set.of(
                    COURSE_CREATE,
                    COURSE_DELETE,
                    COURSE_STUDENT_ADD,
                    COURSE_STUDENT_REMOVE
            )
    ),
    ADMIN(
            Set.of(
                    COURSE_CREATE,
                    COURSE_DELETE,
                    COURSE_STUDENT_ADD,
                    COURSE_STUDENT_REMOVE,
                    USER_ENABLE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
