package org.lrs.kmodernlrs.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lrs.kmodernlrs.Application;
import org.lrs.kmodernlrs.security.UserAccountProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserManagementControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Autowired
    private UserAccountProvider userAccountsProvider;

    private String serviceCreatePath = "/createNewUser";

    private String mockUserJson = "{\"name\":\"tester\",\"email\":\"test@testing.com\", \"password\":\"hideThis\"," +
            "\"role\":\"user\", \"active\":\"true\"}";

    @Test
    public void whenAddingUserThenUserAppearsInDatabase() throws Exception {
        mockClient.perform(
                post(serviceCreatePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockUserJson))
                .andDo(print());
        assertNotNull(userAccountsProvider.getUserAccount("tester"));
    }
}
