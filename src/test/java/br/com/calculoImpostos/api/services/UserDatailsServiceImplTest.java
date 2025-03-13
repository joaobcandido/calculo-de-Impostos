package br.com.calculoImpostos.api.services;


import br.com.calculoImpostos.api.models.RoleEntity;
import br.com.calculoImpostos.api.models.UserEntity;
import br.com.calculoImpostos.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDatailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("testPassword");
        userEntity.setRoleEntities(Set.of(new RoleEntity("ROLE_USER")));

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("testUser");
        });
        verify(userRepository, times(1)).findByUsername("testUser");
    }
}