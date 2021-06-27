package org.vnvika.blog;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void accessAcceptedTest() throws Exception {
        this.mvc.perform(get("/api/articles/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void accessDeniedTest() throws Exception {
        this.mvc.perform(get("/api/articles/my"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void unCorrectLoginTest() throws Exception {
        this.mvc.perform(formLogin().user("vnvika").password("123"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}