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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ActivitiesControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Value("&{auth.basic.username}")
    private String userName;

    @Value("&{auth.basic.password}")
    private String password;

    private String activityPath = "/v1/xAPI/activities";
    private String statementsPath = "/v1/xAPI/statements";
    private String activityID = "testActivityId";

    @Before
    public void postStatement() throws Exception {
        String statementJson = "{\"id\":\"\","+
                "\"actor\":{\"objectType\": \"Agent\",\"name\":\"Project Tin Can API\", "+
                "\"mbox\":\"mailto:user@example.com\"}, "+
                "\"verb\":{\"id\":\"http://adlnet.gov/expapi/verbs/created\"},"+
                "\"object\":{\n  \"id\": \""+activityID+"\",\n  "+
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
    public void whenIncorrectActivityProvidedThenReturnJson() throws Exception {
        String activityIDJson = "{\"activityId\":\"fake\"}";
        mockClient.perform(post(activityPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityIDJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenActivityProvidedThenReturnJson() throws Exception {
        String activityIDJson = "{\"activityId\":\""+activityID+"\"}";
        String expectedActivityJson = "{\"id\":\""+activityID+"\","+
                "\"name\": {\"en-US\":\"simple statement\" }"+
                "},\"description\": {},"+
                "\"moreInfo\":\"\",\"interactionType\":null,"+
                "\"correctResponsesPattern\":[],"+
                "\"choices\":[],\"scale\":[],"+
                "\"source\":[],\"target\":[],\"steps\":[],"+
                "\"extensions\": {}}";
        mockClient.perform(post(activityPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityIDJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedActivityJson));
    }

    private String createBasicAuthHash(String userName, String password) {
        return new String(Base64.encodeBase64((userName+":"+password).getBytes()));
    }
}
