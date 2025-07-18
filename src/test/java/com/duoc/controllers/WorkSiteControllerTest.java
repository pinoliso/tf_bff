package com.duoc.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.duoc.models.WorkSite;
import com.duoc.services.UserService;
import com.duoc.services.WorkSiteService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(WorkSiteController.class)
class WorkSiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkSiteService workSiteService;

    @MockBean
    private UserService userService;

    @Test
    void testCreateWorkSite() throws Exception {
        WorkSite ws = new WorkSite();
        ws.setId(1L);
        ws.setName("Obra Central");

        when(workSiteService.save(any(WorkSite.class))).thenReturn(ws);

        mockMvc.perform(post("/api/worksites")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "name": "Obra Central" }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Obra Central"));
    }
}
