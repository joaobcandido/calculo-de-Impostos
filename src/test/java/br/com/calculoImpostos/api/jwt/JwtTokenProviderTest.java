package br.com.calculoImpostos.api.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    void testGenerateToken() {
        // Arrange
        User user = new User("testUser", "testPassword", Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        // Act
        String token = jwtTokenProvider.generateToken(authentication);

        // Assert
        assertNotNull(token);
    }

    @Test
    void testValidateToken() {
        // Arrange
        User user = new User("testUser", "testPassword", Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testGetUsername() {
        // Arrange
        User user = new User("testUser", "testPassword", Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        String username = jwtTokenProvider.getUsername(token);

        // Assert
        assertEquals("testUser", username);
    }
}