package org.lrs.kmodernlrs.controllers;

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

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;
import static org.lrs.kmodernlrs.TestHelper.createBasicAuthHash;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StatementControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Value("&{auth.basic.username}")
    private String userName;

    @Value("&{auth.basic.password}")
    private String password;

    private String statementsPath = "/v1/xAPI/statements";
    private final String statementId = String.valueOf(ThreadLocalRandom.current().nextLong());
    private String uniqueStatementActivityDescription= "some test statement description";

    private String statementJson = "{\n" +
            "    \"id\":\""+statementId+"\",\n" +
            "    \"timestamp\": \"2015-11-18T12:17:00+00:00\",\n" +
            "    \"actor\":{\n" +
            "        \"objectType\": \"Agent\",\n" +
            "        \"name\":\"Project Tin Can API\",\n" +
            "        \"mbox\":\"mailto:user@example.com\"\n" +
            "    },\n" +
            "    \"verb\":{\n" +
            "        \"id\":\"http://example.com/xapi/verbs#sent-a-statement\",\n" +
            "        \"display\":{ \n" +
            "            \"en-US\":\"sent\" \n" +
            "        }\n" +
            "    },\n" +
            "    \"object\":{\n" +
            "        \"id\":\"http://example.com/xapi/activity/simplestatement\",\n" +
            "        \"definition\":{\n" +
            "            \"name\":{ \n" +
            "                \"en-US\":\"simple statement\" \n" +
            "            },\n" +
            "            \"description\":{ \n" +
            "                \"en-US\":\""+uniqueStatementActivityDescription+"\" \n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";

    @Before
    public void postStatement() throws Exception {
        mockClient.perform(
                post(statementsPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .header(Constants.XAPI_VERSION_HEADER, "1.0.3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(statementJson))
                .andDo(print());
    }

    @Test
    public void whenExistingStatementIdProvidedThenReturnStatementJson() throws Exception {
        MvcResult result = mockClient.perform(get(statementsPath+"/"+statementId)
        .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(statementId));
        assertTrue(result.getResponse().getContentAsString().contains(uniqueStatementActivityDescription));
    }

    @Test
    public void whenGetWithoutStatementIdProvidedThenReturnAllStatements() throws Exception {
        mockClient.perform(get(statementsPath+"/"+statementId)
            .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void whenPutMethodWithStatementIdProvidedThenReturnStatementJson() throws Exception {
        String statementJson = "{\n" +
                "    \"id\":\"\",\n" +
                "    \"timestamp\": \"2015-11-18T12:17:00+00:00\",\n" +
                "    \"actor\":{\n" +
                "        \"objectType\": \"Agent\",\n" +
                "        \"name\":\"Project Tin Can API\",\n" +
                "        \"mbox\":\"mailto:user@example.com\"\n" +
                "    },\n" +
                "    \"verb\":{\n" +
                "        \"id\":\"http://example.com/xapi/verbs#sent-a-statement\"\n" +
                "    },\n" +
                "    \"object\":{\n" +
                "    }\n" +
                "}";
        mockClient.perform(put(statementsPath+"/"+String.valueOf(ThreadLocalRandom.current().nextLong()))
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .header(Constants.XAPI_VERSION_HEADER, "1.0.3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(statementJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

}
