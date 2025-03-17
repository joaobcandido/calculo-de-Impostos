package br.com.calculoImpostos.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserEntityTest {
    @Test
    void testUserEntityGettersAndSetters() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password123");

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ROLE_USER");

        user.setRoleEntities(Set.of(role));

        // Act & Assert
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("testUser", user.getUsername());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertEquals(1, user.getRoleEntities().size());
        Assertions.assertTrue(user.getRoleEntities().contains(role));
    }
}