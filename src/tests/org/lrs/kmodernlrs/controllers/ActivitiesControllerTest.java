package org.lrs.kmodernlrs.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lrs.kmodernlrs.Application;
import org.lrs.kmodernlrs.models.Activity;
import org.lrs.kmodernlrs.repository.RepositoryCustom;
import org.lrs.kmodernlrs.services.ActivitiesService;
import org.lrs.kmodernlrs.services.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
public class ActivitiesControllerTest {

    @Autowired
    private MockMvc mockClient;

    @Autowired
    private ActivitiesService activitiesService;

    @MockBean
    private RepositoryCustom repository;

    @MockBean
    private UserAccountServiceImpl accountProvider;

    @Value("&{auth.basic.username}")
    private String userName;

    @Value("&{auth.basic.password}")
    private String password;

    private String activityPath = "/v1/xAPI/activities";
    private String activityID = "testActivityId";

    @Before
    public void setup() throws Exception {
        when(this.accountProvider.getUserAccountByUsername(userName))
                .thenReturn(createMockUserAccount(userName, password));
    }

    @Test
    public void whenActivityProvidedThenReturnJson() throws Exception {
        Activity expectedActivity = new Activity();
        expectedActivity.setId("12345");
        Map<String, String> nameMap = new HashMap();
        nameMap.put("en-US", "simple statement");
        expectedActivity.setName(nameMap);
        when(this.repository.findById(activityID, Activity.class)).thenReturn(expectedActivity);
        String activityIDJson = "{\"activityId\":\""+activityID+"\"}";
        mockClient.perform(post(activityPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityIDJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedActivity.getId())))
                .andExpect(jsonPath("$.name", is(expectedActivity.getName())));
    }

    @Test
    public void whenIncorrectActivityProvidedThenReturnNotFoundStatus() throws Exception {
        String fakeActivityId = "fake";
        when(this.repository.findById(fakeActivityId, Activity.class))
                .thenThrow(new WebApplicationException(
                Response.status(HttpServletResponse.SC_NOT_FOUND).build()));
        String activityIDJson = "{\"activityId\":\""+fakeActivityId+"\"}";
        mockClient.perform(post(activityPath)
                .header("Authorization", "Basic " + createBasicAuthHash(userName, password))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(activityIDJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
