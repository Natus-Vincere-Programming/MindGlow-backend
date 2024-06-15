package com.natusvincere.mindglow.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChangeCredentialsRequest {
    private String email;
    private String password;
    private String lastName;
    private String firstName;
}
