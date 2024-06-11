package com.natusvincere.mindglow.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>, CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    Page<User> findAllByEnabled(boolean enabled, Pageable pageable);
    Page<User> findAllByLastnameIsStartingWithAndEnabled(String startWith, boolean enabled, Pageable pageable);
    Page<User> findAllByFirstnameIsStartingWithAndEnabled(String startWith, boolean enabled, Pageable pageable);
    Page<User> findAllByEmailIsStartingWithAndEnabled(String startWith, boolean enabled, Pageable pageable);
}
