package br.com.calculoImpostos.api.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RolesTest {

    @Test
    void testRolesEnumValues() {
        // Act
        Roles[] roles = Roles.values();

        // Assert
        Assertions.assertEquals(2, roles.length, "A enumeração Roles deve conter 2 valores.");
        Assertions.assertEquals(Roles.ADMIN, roles[0], "O primeiro valor deve ser ADMIN.");
        Assertions.assertEquals(Roles.USER, roles[1], "O segundo valor deve ser USER.");
    }

    @Test
    void testRolesEnumValueOf() {
        // Act & Assert
        Assertions.assertEquals(Roles.ADMIN, Roles.valueOf("ADMIN"), "Deve retornar ADMIN para o valor 'ADMIN'.");
        Assertions.assertEquals(Roles.USER, Roles.valueOf("USER"), "Deve retornar USER para o valor 'USER'.");
    }
}