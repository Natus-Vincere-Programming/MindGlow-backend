package com.natusvincere.mindglow.auth;

import com.natusvincere.mindglow.config.JwtService;
import com.natusvincere.mindglow.token.Token;
import com.natusvincere.mindglow.token.TokenRepository;
import com.natusvincere.mindglow.user.Role;
import com.natusvincere.mindglow.user.User;
import com.natusvincere.mindglow.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerShouldSaveUserAndGenerateTokens() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe@example.com", "password", Role.STUDENT);
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.STUDENT);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        AuthenticationResponse response = authenticationService.register(request);

        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void authenticateShouldAuthenticateUserAndGenerateTokens() {
        AuthenticationRequest request = new AuthenticationRequest("john.doe@example.com", "password");
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.STUDENT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenRepository, times(1)).save(any(Token.class));
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void refreshTokenShouldGenerateNewAccessToken() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.STUDENT);

        when(request.getHeader(anyString())).thenReturn("Bearer refreshToken");
        when(jwtService.extractUsername(anyString())).thenReturn("john.doe@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(anyString(), any(User.class))).thenReturn(true);
        when(jwtService.generateToken(any(User.class))).thenReturn("newAccessToken");

        authenticationService.refreshToken(request, response);

        verify(tokenRepository, times(1)).save(any(Token.class));
    }
}