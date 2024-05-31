package com.natusvincere.mindglow.course.request;

public record RemoveStudentRequest(
        Integer courseId,
        Integer studentId
) {
}
