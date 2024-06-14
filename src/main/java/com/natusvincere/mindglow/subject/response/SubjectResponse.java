package com.natusvincere.mindglow.subject.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponse {
    private String id;
    private String name;
    private String description;
    private String code;
    @JsonProperty("teacher_name")
    private String teacherName;
}
