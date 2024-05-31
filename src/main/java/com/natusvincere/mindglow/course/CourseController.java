package com.natusvincere.mindglow.course;

import com.natusvincere.mindglow.course.request.AddStudentRequest;
import com.natusvincere.mindglow.course.request.CreateCourseRequest;
import com.natusvincere.mindglow.course.request.DeleteCourseRequest;
import com.natusvincere.mindglow.course.request.RemoveStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PostMapping()
    @PreAuthorize("hasAuthority('course:create')")
    public ResponseEntity<?> create(@RequestBody CreateCourseRequest request, Principal principal) {
        service.createCourse(request, principal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('course:delete')")
    public ResponseEntity<?> delete(@RequestBody DeleteCourseRequest request, Principal principal) {
        service.deleteCourse(request, principal);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/students")
    @PreAuthorize("hasAuthority('course:student:add')")
    public ResponseEntity<?> addStudent(@RequestBody AddStudentRequest request, Principal principal) {
        service.addStudent(request, principal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/students")
    @PreAuthorize("hasAuthority('course:student:remove')")
    public ResponseEntity<?> removeStudent(@RequestBody RemoveStudentRequest request, Principal principal) {
        service.removeStudent(request, principal);
        return ResponseEntity.ok().build();
    }
}
