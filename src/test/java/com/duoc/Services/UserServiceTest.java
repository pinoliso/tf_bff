package com.duoc.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.models.User;
import com.duoc.services.UserService;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        User saved = userService.save(user);

        assertNotNull(saved.getId());
        assertEquals("Admin", saved.getName());
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        userService.save(user);
        User found = userService.findByEmail("admin@email.com");

        assertNotNull(found);
        assertEquals("Admin", found.getName());
    }
}

