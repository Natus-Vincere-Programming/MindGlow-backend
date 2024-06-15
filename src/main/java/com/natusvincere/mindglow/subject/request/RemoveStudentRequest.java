package com.natusvincere.mindglow.subject.request;

public record RemoveStudentRequest(
        Integer subjectId,
        Integer studentId
) {
}
