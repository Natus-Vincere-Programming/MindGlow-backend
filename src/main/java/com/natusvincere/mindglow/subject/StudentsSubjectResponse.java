package com.natusvincere.mindglow.subject;

import com.natusvincere.mindglow.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class StudentsSubjectResponse {

    Collection<UserResponse> students;
}
