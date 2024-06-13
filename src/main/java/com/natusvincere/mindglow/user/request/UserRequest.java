package com.natusvincere.mindglow.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
        private int id;
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String role;
        private boolean enabled;
}
