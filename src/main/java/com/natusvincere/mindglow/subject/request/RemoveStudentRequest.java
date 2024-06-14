package com.natusvincere.mindglow.subject.request;

public record RemoveStudentRequest(
        Integer courseId,
        Integer studentId
) {
}
