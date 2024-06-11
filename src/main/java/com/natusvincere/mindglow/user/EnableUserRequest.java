package com.natusvincere.mindglow.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnableUserRequest {

    private int id;
    private String firstName;
    private String lastName;
    private Role role;
}
