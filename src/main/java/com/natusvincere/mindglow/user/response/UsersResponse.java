package com.natusvincere.mindglow.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponse {

    private Collection<UserResponse> users;
    private boolean hasNext;
}
