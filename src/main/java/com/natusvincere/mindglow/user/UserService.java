package com.natusvincere.mindglow.user;

import com.natusvincere.mindglow.config.JwtService;
import com.natusvincere.mindglow.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
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
     * @param request the request containing the current password and the new password
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

    public UserEnabledResponse getUser(String token) {
        if (!jwtService.isJwtToken(token)) {
            return null;
        }
        var isTokenValid = tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElseThrow();
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        return new UserEnabledResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole(),
                user.isEnabled()
        );
    }

    public UserEnabledResponse getUser(int id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserEnabledResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole(),
                user.isEnabled()
        );
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
        return stream.map(user -> new UserResponse(user.getId(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getRole()))
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
}
