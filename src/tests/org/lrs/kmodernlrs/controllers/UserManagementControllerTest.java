package org.lrs.kmodernlrs.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lrs.kmodernlrs.Application;
import org.lrs.kmodernlrs.models.Entity;
import org.lrs.kmodernlrs.repository.RepositoryCustom;
import org.lrs.kmodernlrs.security.UserAccount;
import org.lrs.kmodernlrs.security.UserAccountProvider;
import org.lrs.kmodernlrs.services.UserAccountServiceImpl;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserManagementControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Autowired
    private UserAccountProvider userAccountsProvider;

    @Autowired
    private UserAccountServiceImpl accountService;

    @MockBean
    private RepositoryCustom repository;

    private String serviceCreatePath = "/createNewUser";

    @Test
    @SuppressWarnings("unchecked")
    public void whenAddingUserThenUserAppearsInDatabase() throws Exception {
        String mockUser = "test";
        String mockPassword = "test";
        String mockEmail = "test@testing.com";
        String mockRole = "user";
        String mockId = "12345";
        boolean mockActive = true;
        UserAccount expectedUser = new UserAccount();
        expectedUser.setName(mockUser);
        expectedUser.setPassword(mockPassword);
        expectedUser.setEmail(mockEmail);
        expectedUser.setRole(mockRole);
        expectedUser.setActive(mockActive);
        expectedUser.setId(mockId);
        doAnswer(new Answer() {
            public Entity answer(InvocationOnMock invocation){
                return null;
            }
        }).when(this.repository).create(expectedUser);
        when(this.repository.exists(mockId, UserAccount.class)).thenReturn(true);
        String mockUserJson = "{\"name\":\""+mockUser+"\"," +
                               "\"email\":\""+mockEmail+"\"," +
                               "\"password\":\""+mockPassword+"\"," +
                               "\"role\":\""+mockRole+"\"," +
                               "\"active\":\""+mockActive+"\"," +
                               "\"id\":\""+mockId+"\"}";
        mockClient.perform(post(serviceCreatePath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUserJson))
                .andDo(print())
                .andExpect(status().isCreated());

    }
}
