package com.natusvincere.mindglow.subject;

import com.natusvincere.mindglow.subject.request.AddStudentRequest;
import com.natusvincere.mindglow.subject.request.CreateCourseRequest;
import com.natusvincere.mindglow.subject.request.RemoveStudentRequest;
import com.natusvincere.mindglow.exception.HttpForbiddenException;
import com.natusvincere.mindglow.subject.response.SubjectResponse;
import com.natusvincere.mindglow.user.Role;
import com.natusvincere.mindglow.user.User;
import com.natusvincere.mindglow.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectResponse createCourse(CreateCourseRequest request, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Subject subject = Subject.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        if (user.getRole() == Role.ADMIN) {
            subject.setTeacher(userRepository.findById(request.getTeacherId()).orElseThrow());
        } else subject.setTeacher(user);
        Subject savedSubject = subjectRepository.save(subject);
        generateCode(savedSubject.getId());
        return mapSubjectToResponse(savedSubject);
    }

    public void deleteCourse(String id, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (user.getRole() == Role.ADMIN) {
            subjectRepository.deleteById(Integer.parseInt(id));
            return;
        }
        Subject subject = subjectRepository.findByTeacherAndId(user, Integer.parseInt(id))
                .orElseThrow(
                        () -> new HttpForbiddenException("You cannot delete a course that is not your own")
                );
        subjectRepository.delete(subject);
    }

    private void generateCode(Integer courseId) {
        String code = generateSixCharacterCode();
        if (subjectRepository.existsByCode(code)) {
            generateCode(courseId);
        }
        Subject subject = subjectRepository.findById(courseId).orElseThrow();
        subject.setCode(code);
        subjectRepository.save(subject);
    }

    public void addStudent(AddStudentRequest request, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        User student = userRepository.findById(request.getStudentId()).orElseThrow();
        if (user.getRole() == Role.ADMIN) {
            Subject subject = subjectRepository.findById(request.getCourseId()).orElseThrow();
            subject.getStudents().add(student);
            subjectRepository.save(subject);
            return;
        }
        Subject subject = subjectRepository.findByTeacherAndId(user, request.getCourseId())
                .orElseThrow(
                        () -> new HttpForbiddenException("You cannot add students to another teacher's course")
                );
        subject.getStudents().add(student);
        subjectRepository.save(subject);
    }

    public void removeStudent(RemoveStudentRequest request, Principal principal) {
        User user = getUser(principal);
        User student = userRepository.findById(request.studentId()).orElseThrow();
        if (user.getRole() == Role.ADMIN) {
            Subject subject = subjectRepository.findById(request.courseId()).orElseThrow();
            subject.getStudents().remove(student);
            subjectRepository.save(subject);
            return;
        }
        Subject subject = subjectRepository.findByTeacherAndId(user, request.courseId())
                .orElseThrow(
                        () -> new HttpForbiddenException("You cannot remove students from another teacher's course")
                );
        subject.getStudents().remove(student);
        subjectRepository.save(subject);
    }

    private static User getUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    private String generateSixCharacterCode() {
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz123456789";
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            code.append(randomChar);
        }

        return code.toString();
    }

    public List<SubjectResponse> getAllSubjects(Principal principal) {
        User user = getUser(principal);
        if (user.getRole() == Role.ADMIN){
            return mapSubjectToResponse(subjectRepository.findAll());
        }
        if (user.getRole() == Role.TEACHER) {
            return mapSubjectToResponse(subjectRepository.findAllByTeacher(user));
        }
        return mapSubjectToResponse(subjectRepository.findAllByStudentsContaining(user));
    }

    private List<SubjectResponse> mapSubjectToResponse(List<Subject> subjects) {
        return subjects.stream().map(subject -> SubjectResponse.builder()
                .teacherName(String.format("%s %s", subject.getTeacher().getLastname(), subject.getTeacher().getFirstname()))
                .code(subject.getCode())
                .name(subject.getName())
                .description(subject.getDescription())
                .id(subject.getId().toString())
                .build()).toList();
    }
    private SubjectResponse mapSubjectToResponse(Subject subject) {
        return SubjectResponse.builder()
                .teacherName(String.format("%s %s", subject.getTeacher().getLastname(), subject.getTeacher().getFirstname()))
                .code(subject.getCode())
                .name(subject.getName())
                .description(subject.getDescription())
                .id(subject.getId().toString())
                .build();
    }
}
