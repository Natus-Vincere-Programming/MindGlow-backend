package com.natusvincere.mindglow.user.request;

import com.natusvincere.mindglow.user.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Deprecated
public class EnableUserRequest {

    private int id;
    private String firstName;
    private String lastName;
    private Role role;
}
