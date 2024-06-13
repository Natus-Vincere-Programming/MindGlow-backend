package com.natusvincere.mindglow.user.request;

import com.natusvincere.mindglow.user.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest extends Pagination {
    private String startLastnameWith;
    private boolean enabled;

    public UsersRequest(int pageNumber, int pageSize, boolean enabled) {
        super(pageNumber, pageSize);
        this.enabled = enabled;
    }
}
