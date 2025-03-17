package br.com.calculoImpostos.api.jwt;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    void testDoFilterInternalWithValidToken() throws ServletException, IOException, java.io.IOException {
        // Arrange
        String token = "validToken";
        String username = "testUser";
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        Mockito.when(jwtTokenProvider.getUsername(token)).thenReturn(username);
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(jwtTokenProvider).validateToken(token);
        Mockito.verify(jwtTokenProvider).getUsername(token);
        Mockito.verify(userDetailsService).loadUserByUsername(username);
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws ServletException, IOException, java.io.IOException {
        // Arrange
        String token = "invalidToken";

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        Mockito.when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(jwtTokenProvider).validateToken(token);
        Mockito.verifyNoInteractions(userDetailsService);
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithoutToken() throws ServletException, IOException, java.io.IOException {
        // Arrange
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verifyNoInteractions(jwtTokenProvider, userDetailsService);
        Mockito.verify(filterChain).doFilter(request, response);
    }
}