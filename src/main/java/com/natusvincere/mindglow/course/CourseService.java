package com.natusvincere.mindglow.course;

import com.natusvincere.mindglow.course.request.AddStudentRequest;
import com.natusvincere.mindglow.course.request.CreateCourseRequest;
import com.natusvincere.mindglow.course.request.DeleteCourseRequest;
import com.natusvincere.mindglow.course.request.RemoveStudentRequest;
import com.natusvincere.mindglow.exception.HttpForbiddenException;
import com.natusvincere.mindglow.user.Role;
import com.natusvincere.mindglow.user.User;
import com.natusvincere.mindglow.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public void createCourse(CreateCourseRequest request, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Course course = Course.builder()
                .teacher(user)
                .name(request.name())
                .section(request.section())
                .topic(request.topic())
                .build();
        Course savedCourse = courseRepository.save(course);
        generateCode(savedCourse.getId());
    }

    public void deleteCourse(DeleteCourseRequest request, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (user.getRole() == Role.ADMIN) {
            courseRepository.deleteById(request.courseId());
            return;
        }
        Course course = courseRepository.findByTeacherAndId(user, request.courseId())
                .orElseThrow(
                        () -> new HttpForbiddenException("You cannot delete a course that is not your own")
                );
        courseRepository.delete(course);
    }

    private void generateCode(Integer courseId) {
        String code = generateSixCharacterCode();
        if (courseRepository.existsByCode(code)) {
            generateCode(courseId);
        }
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.setCode(code);
        courseRepository.save(course);
    }

    public void addStudent(AddStudentRequest request, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        User student = userRepository.findById(request.studentId()).orElseThrow();
        if (user.getRole() == Role.ADMIN) {
            Course course = courseRepository.findById(request.courseId()).orElseThrow();
            course.getStudents().add(student);
            courseRepository.save(course);
            return;
        }
        Course course = courseRepository.findByTeacherAndId(user, request.courseId())
                .orElseThrow(
                        () -> new HttpForbiddenException("You cannot add students to another teacher's course")
                );
        course.getStudents().add(student);
        courseRepository.save(course);
    }

    public void removeStudent(RemoveStudentRequest request, Principal principal) {
        User user = getUser(principal);
        User student = userRepository.findById(request.studentId()).orElseThrow();
        if (user.getRole() == Role.ADMIN) {
            Course course = courseRepository.findById(request.courseId()).orElseThrow();
            course.getStudents().remove(student);
            courseRepository.save(course);
            return;
        }
        Course course = courseRepository.findByTeacherAndId(user, request.courseId())
                .orElseThrow(
                        () -> new HttpForbiddenException("You cannot remove students from another teacher's course")
                );
        course.getStudents().remove(student);
        courseRepository.save(course);
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
}
