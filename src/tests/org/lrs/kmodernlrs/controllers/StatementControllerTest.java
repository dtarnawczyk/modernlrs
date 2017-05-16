package org.lrs.kmodernlrs.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lrs.kmodernlrs.Application;
import org.lrs.kmodernlrs.Constants;
import org.lrs.kmodernlrs.domain.*;
import org.lrs.kmodernlrs.domain.repository.RepositoryCustom;
import org.lrs.kmodernlrs.services.StatementService;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.lrs.kmodernlrs.TestHelper.createBasicAuthHash;
import static org.lrs.kmodernlrs.TestHelper.createMockUserAccount;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StatementControllerTest {

    @Autowired
    private MockMvc mockClient;

    @MockBean
    private UserAccountServiceImpl accountProvider;

    @Autowired
    private StatementService statementService;

    @MockBean
    private RepositoryCustom repository;

    @Value("&{auth.basic.username}")
    private String userName;

    @Value("&{auth.basic.password}")
    private String password;

    private String statementsPath = "/v1/xAPI/statements";
    private final String statementId = String.valueOf(ThreadLocalRandom.current().nextLong());
    private String uniqueStatementActivityDescription= "some test statement description";

    @Before
    public void postStatement() throws Exception {
        when(this.accountProvider.getUserAccountByUsername(userName))
            .thenReturn(createMockUserAccount(userName, password));
    }

    @Test
    public void whenExistingStatementIdProvidedThenReturnStatementJson() throws Exception {
        Statement mockStatement = createMockStatement();
        when(this.repository.findById(this.statementId, Statement.class)).thenReturn(mockStatement);
        mockClient.perform(get(statementsPath+"/"+statementId)
        .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(mockStatement.getId())))
        .andExpect(jsonPath("$.object.definition.description.en-EN", is(uniqueStatementActivityDescription)));
    }

    @Test
    public void whenGetWithoutStatementIdProvidedThenReturnAllStatements() throws Exception {
        when(this.repository.findAll(Statement.class)).thenReturn(Arrays.asList(createMockStatement()));
        mockClient.perform(get(statementsPath)
            .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPutMethodWithStatementIdProvidedThenReturnStatusOk() throws Exception {
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
                .andExpect(status().isCreated());
    }

    private Statement createMockStatement() {
        Statement statement = new Statement();
        statement.setId(statementId);
        statement.setTimestamp("2015-11-18T12:17:00+00:00");
        Actor mockActor = new Actor();
        mockActor.setObjectType("Agent");
        mockActor.setName("Project Tin Can API");
        mockActor.setMbox("mailto:user@example.com");
        statement.setActor(mockActor);
        Verb mockVerb = new Verb();
        mockVerb.setId("http://example.com/xapi/verbs#sent-a-statement");
        Map<String, String> displayLang = new HashMap();
        displayLang.put("en-EN", "sent");
        mockVerb.setDisplay(displayLang);
        statement.setVerb(mockVerb);
        XapiObject mockObject = new XapiObject();
        mockObject.setId("http://example.com/xapi/activity/simplestatement");
        Map<String, String> description = new HashMap<>();
        description.put("en-EN", uniqueStatementActivityDescription);
        Activity mockActivity = new Activity();
        mockActivity.setDescription(description);
        mockObject.setDefinition(mockActivity);
        statement.setXapiObj(mockObject);
        return statement;
    }

}
