package com.natusvincere.mindglow.course;

import com.natusvincere.mindglow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByCode(String code);
    Optional<Course> findByTeacherAndId(User teacher, Integer id);
}
