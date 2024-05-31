package com.natusvincere.mindglow.course.request;

public record CreateCourseRequest(
        String name,
        String section,
        String topic
) {
}
