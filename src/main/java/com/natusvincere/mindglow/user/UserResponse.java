package com.natusvincere.mindglow.user;

public record UserResponse (
        int userId,
        String email,
        String firstName,
        String lastName,
        Role role
){
}
