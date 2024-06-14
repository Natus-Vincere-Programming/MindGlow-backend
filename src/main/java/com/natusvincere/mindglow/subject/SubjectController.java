package com.natusvincere.mindglow.subject;

import com.natusvincere.mindglow.subject.request.AddStudentRequest;
import com.natusvincere.mindglow.subject.request.CreateCourseRequest;
import com.natusvincere.mindglow.subject.request.RemoveStudentRequest;
import com.natusvincere.mindglow.subject.response.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @PostMapping()
    @PreAuthorize("hasAuthority('course:create')")
    public ResponseEntity<SubjectResponse> create(@RequestBody CreateCourseRequest request, Principal principal) {
        return ResponseEntity.ok(service.createCourse(request, principal));
    }

    @GetMapping()
    public ResponseEntity<List<SubjectResponse>> getSubjects(Principal principal) {
        return ResponseEntity.ok(service.getAllSubjects(principal));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('course:delete')")
    public ResponseEntity<?> delete(Principal principal, @PathVariable String id) {
        service.deleteCourse(id, principal);
        return ResponseEntity.noContent().build();
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
