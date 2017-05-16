package org.lrs.kmodernlrs.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lrs.kmodernlrs.Application;
import org.lrs.kmodernlrs.domain.Actor;
import org.lrs.kmodernlrs.domain.repository.RepositoryCustom;
import org.lrs.kmodernlrs.services.AgentsService;
import org.lrs.kmodernlrs.services.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.lrs.kmodernlrs.TestHelper.createBasicAuthHash;
import static org.lrs.kmodernlrs.TestHelper.createMockUserAccount;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AgentsControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Autowired
    private AgentsService agentService;

    @MockBean
    private RepositoryCustom repository;

    @MockBean
    private UserAccountServiceImpl accountProvider;

    @Value("&{auth.basic.username}")
    private String userName;

    @Value("&{auth.basic.password}")
    private String password;

    private String agentsPath = "/v1/xAPI/agents";
    private String testAgentMail = "mailto:testuser@example.com";


    @Before
    public void setup() throws Exception {
        when(this.accountProvider.getUserAccountByUsername(userName))
                .thenReturn(createMockUserAccount(userName, password));
    }

    @Test
    public void whenAvailableActorsMailProvidedThenReturnActorDetailsJson() throws Exception {
        Actor expectedActor = createMockActor();
        Map<String, String> actorAttributes = new HashMap<>();
        actorAttributes.put("mbox", testAgentMail);
        when(this.repository.findByAttrs(actorAttributes, Actor.class)).thenReturn(expectedActor);
        String agentJson = "{\"mbox\":\""+testAgentMail+"\"}";
        mockClient.perform(post(agentsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(agentJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedActor.getId())))
                .andExpect(jsonPath("$.name", is(expectedActor.getName())))
                .andExpect(jsonPath("$.openid", is(expectedActor.getOpenid())))
                .andExpect(jsonPath("$.objectType", is(expectedActor.getObjectType())))
                .andExpect(jsonPath("$.mbox", is(expectedActor.getMbox())));
    }

    @Test
    public void whenNoActorsMailProvidedThenReturnNoContent() throws Exception {
        mockClient.perform(post(agentsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenIncorrectActivityProvidedThenReturnNotFoundStatus() throws Exception {
        Map<String, String> fakeMbox = new HashMap<>();
        fakeMbox.put("mbox", "fake");
        when(this.repository.findByAttrs(fakeMbox, Actor.class)).thenReturn(null);
        String agentFakeMboxJson = "{\"mbox\":\"fake\"}";
        mockClient.perform(post(agentsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(agentFakeMboxJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Actor createMockActor(){
        String testAgentName = "testagent";
        String testMboxSha1sum = "mboxSha1sum";
        String testObjectType = "testObjectType";
        String testOpenId = "testOpenId";
        String testAgentId = "1234567";
        Actor expectedActor = new Actor();
        expectedActor.setId(testAgentId);
        expectedActor.setMbox(testAgentMail);
        expectedActor.setName(testAgentName);
        expectedActor.setMbox_sha1sum(testMboxSha1sum);
        expectedActor.setObjectType(testObjectType);
        expectedActor.setOpenid(testOpenId);
        return expectedActor;
    }
}
