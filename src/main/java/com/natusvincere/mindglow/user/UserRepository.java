package com.natusvincere.mindglow.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>, CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findAllByEnabled(boolean enabled, Pageable pageable);

    List<User> findAllByEnabled(boolean enabled);

    Page<User> findAllByLastnameIsStartingWithAndEnabled(String startWith, boolean enabled, Pageable pageable);

    Page<User> findAllByFirstnameIsStartingWithAndEnabled(String startWith, boolean enabled, Pageable pageable);

    Page<User> findAllByEmailIsStartingWithAndEnabled(String startWith, boolean enabled, Pageable pageable);

    List<User> findAllByRoleAndEnabled(Role role, boolean enabled);

    List<User> findAllByFirstnameStartingWithAndRoleAndEnabled(String firstname, Role role, boolean enabled);

    List<User> findAllByLastnameStartingWithAndRoleAndEnabled(String lastname, Role role, boolean enabled);

    List<User> findAllByEmailStartingWithAndRoleAndEnabled(String email, Role role, boolean enabled);

    Slice<User> findAllByFirstnameStartingWithAndRoleAndEnabled(String firstname, Role role, Pageable pageable, boolean enabled);
    Slice<User> findAllByLastnameStartingWithAndRoleAndEnabled(String lastname, Role role, Pageable pageable, boolean enabled);
    Slice<User> findAllByEmailStartingWithAndRoleAndEnabled(String email, Role role, Pageable pageable, boolean enabled);
    Slice<User> findAllByRoleAndEnabled(Role role, Pageable pageable, boolean enabled);

    List<User> findAllByFirstnameStartingWithAndEnabled(String firstname, boolean enabled);

    List<User> findAllByLastnameStartingWithAndEnabled(String startWith, boolean enabled);

    List<User> findAllByEmailStartingWithAndEnabled(String startWith, boolean enabled);

    Slice<User> findAllByFirstnameStartingWithAndEnabled(String startWith, Pageable pageable, boolean enabled);

    Slice<User> findAllByLastnameStartingWithAndEnabled(String startWith, Pageable pageable, boolean enabled);

    Slice<User> findAllByEmailStartingWithAndEnabled(String startWith, Pageable pageable, boolean enabled);

    Slice<User> findAllByEnabled(Pageable pageable, boolean enabled);

}
