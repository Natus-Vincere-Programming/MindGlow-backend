package com.natusvincere.mindglow.user;

import com.natusvincere.mindglow.config.JwtService;
import com.natusvincere.mindglow.token.TokenRepository;
import com.natusvincere.mindglow.user.request.EnableUserRequest;
import com.natusvincere.mindglow.user.request.UserRequest;
import com.natusvincere.mindglow.user.request.UsersRequest;
import com.natusvincere.mindglow.user.response.PaginationUserResponse;
import com.natusvincere.mindglow.user.response.PupilsResponse;
import com.natusvincere.mindglow.user.response.UserResponse;
import com.natusvincere.mindglow.user.response.UsersResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    /**
     * Change the password of the connected user
     *
     * @param request       the request containing the current password and the new password
     * @param connectedUser the connected user
     */
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }

    public PaginationUserResponse getUsers(UsersRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
        if (request.getStartLastnameWith() == null) {
            Page<User> allByEnabled = userRepository.findAllByEnabled(request.isEnabled(), pageRequest);
            return PaginationUserResponse.builder()
                    .users(mapUsersToResponse(allByEnabled.stream()))
                    .hasNext(allByEnabled.hasNext())
                    .build();
        } else {
            Page<User> allByLastnameIsStartingWithAndEnabled = userRepository.findAllByLastnameIsStartingWithAndEnabled(request.getStartLastnameWith(), request.isEnabled(), pageRequest);
            return PaginationUserResponse.builder()
                    .users(mapUsersToResponse(allByLastnameIsStartingWithAndEnabled.stream()))
                    .hasNext(allByLastnameIsStartingWithAndEnabled.hasNext())
                    .build();
        }
    }

    private List<UserResponse> mapUsersToResponse(Stream<User> stream) {
        return stream.map(user -> UserResponse.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .id(String.valueOf(user.getId()))
                        .build())
                .toList();
    }

    public void enableUser(EnableUserRequest request) {
        User user = userRepository.findById(request.getId()).orElseThrow();
        user.setFirstname(request.getFirstName());
        user.setLastname(request.getLastName());
        user.setRole(request.getRole());
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow();
        tokenRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }

    public PupilsResponse getPupils(boolean enabled) {
        return PupilsResponse.builder()
                .users(mapUsersToResponse(userRepository.findAllByRoleAndEnabled(Role.STUDENT, enabled).stream()))
                .build();
    }

    public PupilsResponse getPupils(String startWith, boolean enabled) {
        Set<UserResponse> users = new HashSet<>();
        users.addAll(mapUsersToResponse(userRepository.findAllByFirstnameStartingWithAndRoleAndEnabled(startWith, Role.STUDENT, enabled).stream()));
        users.addAll(mapUsersToResponse(userRepository.findAllByLastnameStartingWithAndRoleAndEnabled(startWith, Role.STUDENT, enabled).stream()));
        users.addAll(mapUsersToResponse(userRepository.findAllByEmailStartingWithAndRoleAndEnabled(startWith, Role.STUDENT, enabled).stream()));
        return PupilsResponse.builder()
                .users(users)
                .build();
    }

    public PupilsResponse getPupils(int page, int size, String startWith, boolean enabled) {
        boolean hasNext = false;
        PageRequest request = PageRequest.of(page, size);
        Set<UserResponse> users = new HashSet<>();
        Slice<User> allByFirstnameStartingWithAndRole = userRepository.findAllByFirstnameStartingWithAndRoleAndEnabled(startWith, Role.STUDENT, request, enabled);
        Slice<User> allByLastnameStartingWithAndRole = userRepository.findAllByLastnameStartingWithAndRoleAndEnabled(startWith, Role.STUDENT, request, enabled);
        Slice<User> allByEmailStartingWithAndRole = userRepository.findAllByEmailStartingWithAndRoleAndEnabled(startWith, Role.STUDENT, request, enabled);
        if (allByFirstnameStartingWithAndRole.hasNext() || allByLastnameStartingWithAndRole.hasNext() || allByEmailStartingWithAndRole.hasNext()) {
            hasNext = true;
        }
        users.addAll(mapUsersToResponse(allByFirstnameStartingWithAndRole.getContent().stream()));
        users.addAll(mapUsersToResponse(allByLastnameStartingWithAndRole.getContent().stream()));
        users.addAll(mapUsersToResponse(allByEmailStartingWithAndRole.getContent().stream()));
        return PupilsResponse.builder()
                .users(users)
                .hasNext(hasNext)
                .build();
    }

    public PupilsResponse getPupils(int page, int size, boolean enabled) {
        PageRequest request = PageRequest.of(page, size);
        Slice<User> allByRole = userRepository.findAllByRoleAndEnabled(Role.STUDENT, request, enabled);
        return PupilsResponse.builder()
                .users(mapUsersToResponse(allByRole.getContent().stream()))
                .hasNext(allByRole.hasNext())
                .build();
    }

    public UserResponse getUser(int id) {
        return userRepository.findById(id)
                .map(user -> UserResponse.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .id(String.valueOf(user.getId()))
                        .enabled(user.isEnabled())
                        .build())
                .orElseThrow();
    }

    public UserResponse getUser(String token) {
        var exists = tokenRepository.existsByTokenAndExpiredAndRevoked(token, false, false);
        if (!exists) {
            throw new IllegalStateException("Token is not valid");
        }
        String userEmail = jwtService.extractUsername(token);
        return userRepository.findByEmail(userEmail)
                .map(user -> UserResponse.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .id(String.valueOf(user.getId()))
                        .enabled(user.isEnabled())
                        .build())
                .orElseThrow();
    }

    public UsersResponse getUsers(boolean enabled) {
        return UsersResponse.builder()
                .users(mapUsersToResponse(userRepository.findAllByEnabled(enabled).stream()))
                .build();
    }

    public UsersResponse getUsers(String startWith, boolean enabled) {
        Set<UserResponse> users = new HashSet<>();
        users.addAll(mapUsersToResponse(userRepository.findAllByFirstnameStartingWithAndEnabled(startWith, enabled).stream()));
        users.addAll(mapUsersToResponse(userRepository.findAllByLastnameStartingWithAndEnabled(startWith, enabled).stream()));
        users.addAll(mapUsersToResponse(userRepository.findAllByEmailStartingWithAndEnabled(startWith, enabled).stream()));
        return UsersResponse.builder()
                .users(users)
                .build();
    }

    public UsersResponse getUsers(int page, int size, String startWith, boolean enabled) {
        boolean hasNext = false;
        PageRequest request = PageRequest.of(page, size);
        Set<UserResponse> users = new HashSet<>();
        Slice<User> allByFirstnameStartingWithAndEnabled = userRepository.findAllByFirstnameStartingWithAndEnabled(startWith, request, enabled);
        Slice<User> allByLastnameStartingWithAndEnabled = userRepository.findAllByLastnameStartingWithAndEnabled(startWith, request, enabled);
        Slice<User> allByEmailStartingWithAndEnabled = userRepository.findAllByEmailStartingWithAndEnabled(startWith, request, enabled);
        if (allByFirstnameStartingWithAndEnabled.hasNext() || allByLastnameStartingWithAndEnabled.hasNext() || allByEmailStartingWithAndEnabled.hasNext()) {
            hasNext = true;
        }
        users.addAll(mapUsersToResponse(allByFirstnameStartingWithAndEnabled.getContent().stream()));
        users.addAll(mapUsersToResponse(allByLastnameStartingWithAndEnabled.getContent().stream()));
        users.addAll(mapUsersToResponse(allByEmailStartingWithAndEnabled.getContent().stream()));
        return UsersResponse.builder()
                .users(users)
                .hasNext(hasNext)
                .build();
    }

    public UsersResponse getUsers(int page, int size, boolean enabled) {
        PageRequest request = PageRequest.of(page, size);
        Slice<User> allByRole = userRepository.findAllByEnabled(request, enabled);
        return UsersResponse.builder()
                .users(mapUsersToResponse(allByRole.getContent().stream()))
                .hasNext(allByRole.hasNext())
                .build();
    }

    public void changeUser(UserRequest request) {
        User user = userRepository.findById(request.getId()).orElseThrow();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole()));
        user.setEnabled(request.isEnabled());
        userRepository.save(user);
    }
}
