package com.natusvincere.mindglow.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int pageNumber;
    private int pageSize;
}
