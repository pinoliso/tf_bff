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
import com.duoc.models.WorkSite;
import com.duoc.models.WorkSiteStatus;
import com.duoc.models.WorkSiteType;
import com.duoc.services.UserService;
import com.duoc.services.WorkSiteService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@WebMvcTest(WorkSiteController.class)
@Import(TestSecurityConfig.class)
class WorkSiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkSiteService workSiteService;

    @MockBean
    private UserService userService;

    @Test
    void testCreateWorkSite() throws Exception {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userService.save(user);

        WorkSiteStatus status = new WorkSiteStatus();
        status.setName("Inactivo");

        WorkSiteType type = new WorkSiteType();
        type.setName("Tipo 2");

        WorkSite workSite = new WorkSite();
        workSite.setId(1L);
        workSite.setName("Sitio Prueba 2");
        workSite.setUser(user);
        workSite.setWorkSiteStatus(status);
        workSite.setWorkSiteType(type);

        when(workSiteService.saveWithUser(any(WorkSite.class), any(String.class))).thenReturn(workSite);

        mockMvc.perform(post("/api/worksites")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin")))
                .content("""
                    { "name": "Sitio Prueba 2" }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Sitio Prueba 2"));
    }
}
