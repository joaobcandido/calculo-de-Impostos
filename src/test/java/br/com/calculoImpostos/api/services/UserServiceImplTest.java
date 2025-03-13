package br.com.calculoImpostos.api.services;

import br.com.calculoImpostos.api.dtos.RegisterUserDto;
import br.com.calculoImpostos.api.models.RoleEntity;
import br.com.calculoImpostos.api.models.UserEntity;
import br.com.calculoImpostos.api.repositories.RoleRepository;
import br.com.calculoImpostos.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto("joao10", "admin12310", "ADMIN");
        when(userRepository.existsByUsername(registerUserDto.username())).thenReturn(false);
        when(passwordEncoder.encode(registerUserDto.password())).thenReturn("encodedPassword");

        RoleEntity roleEntity = new RoleEntity(registerUserDto.role());
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        // Act
        userService.registerUser(registerUserDto);

        // Assert
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto("joao10", "admin12310", "ADMIN");
        when(userRepository.existsByUsername(registerUserDto.username())).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(registerUserDto);
        });

        assertEquals("Unprocess Entity", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(roleRepository, never()).save(any(RoleEntity.class));
    }
}
