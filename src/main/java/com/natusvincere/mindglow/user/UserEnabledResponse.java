package com.natusvincere.mindglow.user;

public record UserEnabledResponse(
        int userId,
        String email,
        String firstName,
        String lastName,
        Role role,
        boolean enabled
) {
}
