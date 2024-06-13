package com.natusvincere.mindglow.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    COURSE_CREATE("course:create"),
    COURSE_DELETE("course:delete"),
    COURSE_STUDENT_ADD("course:student:add"),
    COURSE_STUDENT_REMOVE("course:student:remove"),
    USER_ENABLE("user:enable"),
    USER_DELETE("user:delete"),
    USER_GET("user:get"),
    USER_UPDATE("user:update");

    private final String permission;
}
