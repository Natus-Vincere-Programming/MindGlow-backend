package com.natusvincere.mindglow.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Deprecated
public class PaginationUserResponse {

    private List<UserResponse> users;
    private boolean hasNext;
}
