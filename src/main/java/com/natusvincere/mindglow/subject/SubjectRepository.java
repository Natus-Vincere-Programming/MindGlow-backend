package com.natusvincere.mindglow.subject;

import com.natusvincere.mindglow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    boolean existsByCode(String code);
    Optional<Subject> findByTeacherAndId(User teacher, Integer id);

    List<Subject> findAllByTeacher(User teacher);
    List<Subject> findAllByStudentsContaining(User student);

    Optional<Subject> findByStudentsContainingAndId(User user, int id);
    Optional<Subject> findByStudentsContaining(User student);
}
