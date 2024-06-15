package com.natusvincere.mindglow.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private boolean enabled;
    private boolean firstLogin;
}
