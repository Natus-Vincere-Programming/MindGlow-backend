package com.natusvincere.mindglow.subject.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseRequest {
    private String name;
    private String description;
    @JsonProperty("teacher_id")
    private int teacherId;
}
