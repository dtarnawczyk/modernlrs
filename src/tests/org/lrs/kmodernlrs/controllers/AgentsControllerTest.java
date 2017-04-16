package org.lrs.kmodernlrs.controllers;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lrs.kmodernlrs.Application;
import org.lrs.kmodernlrs.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AgentsControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Value("&{auth.basic.username}")
    private String userName;

    @Value("&{auth.basic.password}")
    private String password;

    private String agentsPath = "/v1/xAPI/agents";
    private String testAgentMail = "mailto:testuser@example.com";
    private String statementsPath = "/v1/xAPI/statements";

    // when no json with agent then return WebApplicationException

    // when not existing agent id json provided then return not found

    @Before
    public void postStatement() throws Exception {
        String statementJson = "{\"id\":\"\","+
                "\"actor\":{\"objectType\": \"Agent\",\"name\":\"test agent\", "+
                "\"mbox\":\""+testAgentMail+"\"}, "+
                "\"verb\":{\"id\":\"http://adlnet.gov/expapi/verbs/created\"},"+
                "\"object\":{\n  \"id\": \"testobj\",\n  "+
                "\"objectType\" :\"Activity\",\n  "+
                "\"definition\" :{\n  \"name\": {\n  \"en-US\": \"simple statement\" }\n  "+
                "},\n  \"description\": {},"+
                "\n  \"moreInfo\": \"\",\n  \"interactionType\": null,"+
                "\"correctResponsesPattern\": [],\n  "+
                "\"choices\":[],\n\"scale\": [],\n  "+
                "\"source\":[],\"target\":[],\"steps\": [],"+
                "\"extensions\":{}}}";
        mockClient.perform(
                post(statementsPath)
                        .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                        .header(Constants.XAPI_VERSION_HEADER, "1.0.3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statementJson))
                .andDo(print());
    }


    @Test
    public void whenAvailableActorsMailProvidedThenReturnActorDetailsJson() throws Exception {
        String agentJson = "{\"mbox\":\""+testAgentMail+"\"}";
        MvcResult result = mockClient.perform(post(agentsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(agentJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(testAgentMail));
    }

    @Test
    public void whenFakeActorsMailProvidedThenReturnActorDetailsJson() throws Exception {
        mockClient.perform(post(agentsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenIncorrectActivityProvidedThenReturnNotFoundStatus() throws Exception {
        String agentFakeMboxJson = "{\"mbox\":\"fake\"}";
        mockClient.perform(post(agentsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(agentFakeMboxJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String createBasicAuthHash(String userName, String password) {
        return new String(Base64.encodeBase64((userName+":"+password).getBytes()));
    }
}
