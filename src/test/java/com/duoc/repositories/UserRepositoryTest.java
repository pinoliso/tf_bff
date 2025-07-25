package com.duoc.repositories;

import com.duoc.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Guardar y buscar usuario por email")
    void testSaveAndFindByEmail() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("admin@email.com");
        assertThat(found.get()).isNotNull();
        assertThat(found.get().getB2cSub()).isEqualTo("sub-admin");
    }

    @Test
    @DisplayName("Guardar y buscar usuario por sub")
    void testSaveAndFindBySub() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        Optional<User> found = userRepository.findByB2cSub("sub-admin");
        assertThat(found.get()).isNotNull();
        assertThat(found.get().getEmail()).isEqualTo("admin@email.com");
    }

    @Test
    @DisplayName("Eliminar usuario")
    void testDeleteUser() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
