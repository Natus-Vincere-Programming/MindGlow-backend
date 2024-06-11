package com.natusvincere.mindglow.user;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{token}")
    public ResponseEntity<UserEnabledResponse> getUser(@PathVariable String token) {
        return ResponseEntity.ok(service.getUser(token));
    }

    @GetMapping("/id")
    public ResponseEntity<UserEnabledResponse> getUser(@RequestParam int id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @GetMapping()
    public ResponseEntity<PaginationUserResponse> getUser(@ModelAttribute UsersRequest request) {
        return ResponseEntity.ok(service.getUsers(request));
    }

    @PatchMapping("/enable")
    @PreAuthorize("hasAuthority('user:enable')")
    public ResponseEntity<?> enableUser(@RequestBody EnableUserRequest request) {
        service.enableUser(request);
        return ResponseEntity.ok().build();
    }
}
