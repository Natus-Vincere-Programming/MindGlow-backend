package com.natusvincere.mindglow.course.request;

public record AddStudentRequest(
        Integer courseId,
        Integer studentId
) {
}
