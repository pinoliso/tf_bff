package com.duoc.repositories;

import com.duoc.models.User;
import com.duoc.models.WorkSite;
import com.duoc.models.WorkSiteStatus;
import com.duoc.models.WorkSiteType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WorkSiteRepositoryTest {

    @Autowired
    private WorkSiteRepository workSiteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkSiteStatusRepository workSiteStatusRepository;
    @Autowired
    private WorkSiteTypeRepository workSiteTypeRepository;

    @Test
    @DisplayName("Guardar y buscar WorkSite")
    void testSaveAndFindWorkSite() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        WorkSiteStatus status = new WorkSiteStatus();
        status.setName("Activo");
        status = workSiteStatusRepository.save(status);

        WorkSiteType type = new WorkSiteType();
        type.setName("Tipo 1");
        type = workSiteTypeRepository.save(type);

        WorkSite site = new WorkSite();
        site.setName("Sitio 123");
        site.setUser(user);
        site.setWorkSiteStatus(status);
        site.setWorkSiteType(type);
        site = workSiteRepository.save(site);

        List<WorkSite> found = workSiteRepository.findByName("Sitio 123");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getName()).isEqualTo("Sitio 123");
    }

    @Test
    @DisplayName("Eliminar WorkSite")
    void testDeleteWorkSite() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);
        
        WorkSiteStatus status = new WorkSiteStatus();
        status.setName("Activo");
        status = workSiteStatusRepository.save(status);

        WorkSiteType type = new WorkSiteType();
        type.setName("Tipo 1");
        type = workSiteTypeRepository.save(type);

        WorkSite site = new WorkSite();
        site.setName("Sitio 123");
        site.setUser(user);
        site.setWorkSiteStatus(status);
        site.setWorkSiteType(type);
        site = workSiteRepository.save(site);

        workSiteRepository.deleteById(site.getId());
        assertThat(workSiteRepository.findById(site.getId())).isEmpty();
    }
}
