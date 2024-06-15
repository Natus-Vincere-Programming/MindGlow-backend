package com.natusvincere.mindglow.subject;

import com.natusvincere.mindglow.subject.request.AddStudentRequest;
import com.natusvincere.mindglow.subject.request.CreateCourseRequest;
import com.natusvincere.mindglow.subject.response.SubjectResponse;
import com.natusvincere.mindglow.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
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

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubject(Principal principal, @PathVariable String id) {
        return ResponseEntity.ok(service.getSubject(id, principal));
    }

    @DeleteMapping("/{id}")
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
    public ResponseEntity<?> removeStudent(@RequestParam int pupilId, @RequestParam int subjectId, Principal principal) {
        service.removeStudent(pupilId, subjectId, principal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    public ResponseEntity<Collection<UserResponse>> getStudentSubject(@RequestParam int id, Principal principal) {
        return ResponseEntity.ok(service.getStudentSubjects(id, principal));
    }
}
