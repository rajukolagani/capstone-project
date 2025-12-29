package com.corporatebank.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.*;
import com.corporatebank.dto.*;
import com.corporatebank.model.*;
import com.corporatebank.repo.UserRepository;
import com.corporatebank.exception.*;
import com.corporatebank.security.JwtUtil;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authManager;
    @Mock private JwtUtil jwtUtil;
    @InjectMocks private AuthService authService;

    @Test
    void testLogin_InactiveUser() {
        LoginRequest req = new LoginRequest();
        req.setUsername("raju");
        User user = new User();
        user.setActive(false); // Triggers inactive branch

        when(userRepository.findByUsername("raju")).thenReturn(Optional.of(user));
        assertThrows(BadRequestException.class, () -> authService.login(req));
    }

    @Test
    void testRegister_Success() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("newuser");
        req.setRole(Role.RM); // Ensure Role enum exists

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPass");
        when(userRepository.save(any())).thenReturn(new User());

        User result = authService.register(req);
        assertNotNull(result);
    }
}