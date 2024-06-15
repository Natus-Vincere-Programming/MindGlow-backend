package com.natusvincere.mindglow.user;

import com.natusvincere.mindglow.user.request.UserRequest;
import com.natusvincere.mindglow.user.response.PupilsResponse;
import com.natusvincere.mindglow.user.response.TeachersResponse;
import com.natusvincere.mindglow.user.response.UserResponse;
import com.natusvincere.mindglow.user.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserService userService;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasAuthority('user:get')")
    public ResponseEntity<UserResponse>     getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("token/{token}")
    public ResponseEntity<UserResponse> getUserByToken(@PathVariable String token) {
        return ResponseEntity.ok(userService.getUser(token));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('user:get')")
    public ResponseEntity<UsersResponse> getUsers(
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "-1") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(required = false) String startWith,
            @RequestParam(defaultValue = "true") boolean enabled
    ) {
        if (!pagination && startWith == null) {
            return ResponseEntity.ok(service.getUsers(enabled));
        }
        if (!pagination) {
            return ResponseEntity.ok(service.getUsers(startWith, enabled));
        }
        if (startWith != null){
            return ResponseEntity.ok(service.getUsers(page, size, startWith, enabled));
        }
        return ResponseEntity.ok(service.getUsers(page, size, enabled));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<?> putUser(@RequestBody UserRequest request) {
        service.changeUser(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pupils")
    @PreAuthorize("hasAuthority('user:get')")
    public ResponseEntity<PupilsResponse> getPupils(
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "-1") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(required = false) String startWith,
            @RequestParam(defaultValue = "true") boolean enabled
    ) {
        if (!pagination && startWith == null) {
            return ResponseEntity.ok(service.getPupils(enabled));
        }
        if (!pagination) {
            return ResponseEntity.ok(service.getPupils(startWith, enabled));
        }
        if (startWith != null){
            return ResponseEntity.ok(service.getPupils(page, size, startWith, enabled));
        }
        return ResponseEntity.ok(service.getPupils(page, size, enabled));
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasAuthority('user:get')")
    public ResponseEntity<TeachersResponse> getTeachers(
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "-1") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(required = false) String startWith,
            @RequestParam(defaultValue = "true") boolean enabled
    ) {
        if (!pagination && startWith == null) {
            return ResponseEntity.ok(service.getTeachers(enabled));
        }
        if (!pagination) {
            return ResponseEntity.ok(service.getTeachers(startWith, enabled));
        }
        if (startWith != null){
            return ResponseEntity.ok(service.getTeachers(page, size, startWith, enabled));
        }
        return ResponseEntity.ok(service.getTeachers(page, size, enabled));
    }
}
