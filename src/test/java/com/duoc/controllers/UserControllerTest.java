package com.duoc.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.duoc.config.TestSecurityConfig;
import com.duoc.models.User;
import com.duoc.services.UserService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");

        when(userService.newUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin")))
                .content("""
                    {
                        "name": "Admin",
                        "email": "admin@email.com"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Admin"))
            .andExpect(jsonPath("$.email").value("admin@email.com"));
    }
}
