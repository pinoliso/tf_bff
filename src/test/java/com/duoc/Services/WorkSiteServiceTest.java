package com.duoc.Services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.models.User;
import com.duoc.models.WorkSite;
import com.duoc.services.UserService;
import com.duoc.services.WorkSiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;


@SpringBootTest
@Transactional
class WorkSiteServiceTest {

    @Autowired
    private WorkSiteService workSiteService;
    @Autowired
    private UserService userService;

    @Test
    void testCreateWorkSite() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user = userService.save(user);

        WorkSite ws = new WorkSite();
        ws.setUser(user);
        ws.setName("Sitio Prueba");

        WorkSite saved = workSiteService.save(ws);
        assertNotNull(saved.getId());
        assertEquals("Sitio Prueba", saved.getName());
        assertNotNull(saved.getUser());
    }
}

