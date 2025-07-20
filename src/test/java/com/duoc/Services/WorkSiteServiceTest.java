package com.duoc.Services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.models.User;
import com.duoc.models.WorkSite;
import com.duoc.models.WorkSiteStatus;
import com.duoc.models.WorkSiteType;
import com.duoc.repositories.WorkSiteStatusRepository;
import com.duoc.repositories.WorkSiteTypeRepository;
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
    @Autowired
    private WorkSiteStatusRepository workSiteStatusRepository;
    @Autowired
    private WorkSiteTypeRepository workSiteTypeRepository;

    @Test
    void testCreateWorkSite() {
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
        status = workSiteStatusRepository.save(status);

        WorkSiteType type = new WorkSiteType();
        type.setName("Tipo 2");
        type = workSiteTypeRepository.save(type);

        WorkSite site = new WorkSite();
        site.setName("Sitio Prueba 2");
        site.setUser(user);
        site.setWorkSiteStatus(status);
        site.setWorkSiteType(type);
        WorkSite saved = workSiteService.save(site);

        assertNotNull(saved.getId());
        assertEquals("Sitio Prueba 2", saved.getName());
        assertNotNull(saved.getUser());
    }
}

