package org.lrs.kmodernlrs

import org.apache.commons.codec.binary.Base64
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
open class WebTest {
	
	@Autowired lateinit var mockClient: MockMvc
	
	@Value("&{auth.basic.username}")
	lateinit var userName:String
	
	@Value("&{auth.basic.password}")
	lateinit var password:String

    @Value("&{dashboard.user.name}")
    lateinit var dashUserName:String

    @Value("&{dashboard.user.password}")
    lateinit var dashUserPassword:String

	
//	val log = LoggerFactory.getLogger(WebTest::class.java)
	
	val statementsPath: String = "/v1/xAPI/statements"
	val agentsPath:String = "/v1/xAPI/agents"
	val activityPath:String = "/v1/xAPI/activities"
	val dashboardPath:String = "/"
	
	@Test
	fun basicAuthTest() {
		mockClient.perform(get(statementsPath)
			.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
	}

//	@Test
//	fun dashboardAuthTest() {
//        mockClient.perform(get(dashboardPath)
//                .header("Authorization", "Basic " + createBasicAuthHash(dashUserName, dashUserPassword)))
//                .andExpect(status().isOk)
//	}

	@Test
	fun postGetStatementIdTest() {
		val statementId = UUID.randomUUID().toString()
		val statementJson: String = "{\"id\":\""+statementId+"\","+
							"\"actor\":{\"objectType\": \"Agent\",\"name\":\"Project Tin Can API\", "+
							"\"mbox\":\"mailto:user@example.com\"}, "+
							"\"verb\":{\"id\":\"http://adlnet.gov/expapi/verbs/created\","+
							"\"display\":{\"en-US\":\"created\" }},"+
							"\"object\":{\"id\":\"http://example.adlnet.gov/xapi/example/simplestatement\","+
							"\"definition\":{\"name\":{ \"en-US\":\"simple statement\"},"+
							"\"description\":{ \"en-US\":\"A simple Experience API statement. Note that the LRS does not need to have any prior information about the Actor (learner), the "+
							"verb, or the Activity/object.\" }}}}"
		mockClient.perform(
			post(statementsPath)
				.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
				.header(Constants.XAPI_VERSION_HEADER, "1.0.3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(statementJson))
				.andDo(print())
				.andExpect(status().isOk)
				.andExpect(header().string("X-Experience-API-Version", "1.0.3"))

		mockClient.perform(get(statementsPath+"/"+statementId)
			.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
            .accept(MediaType.APPLICATION_JSON))
			.andDo(print())
            .andExpect(status().isOk)
	}

	@Test
	fun postGetAgentOnMbox() {
		val statementId = UUID.randomUUID().toString()
		val mbox: String = "mailto:user123@example.com"
		val statementJson: String = "{\"id\":\""+statementId+"\","+
				"\"actor\":{\"objectType\": \"Agent\",\"name\":\"Project Tin Can API\", "+
				"\"mbox\":\""+mbox+"\"}, "+
				"\"verb\":{\"id\":\"http://adlnet.gov/expapi/verbs/created\","+
				"\"display\":{\"en-US\":\"created\" }},"+
				"\"object\":{\"id\":\"http://example.adlnet.gov/xapi/example/simplestatement\","+
				"\"definition\":{\"name\":{ \"en-US\":\"simple statement\"}"+
				"}}}"
		val agentJson: String = "{\"mbox\":\""+mbox+"\"}"
		val expectedAgentJson = "{\"objectType\":\"Agent\",\"name\":\"Project Tin Can API\","+
				"\"mbox\":\""+mbox+"\"}"
		mockClient.perform(
				post(statementsPath)
				.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
				.header(Constants.XAPI_VERSION_HEADER, "1.0.3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(statementJson))
				.andDo(print())
				.andExpect(status().isOk)
				.andExpect(header().string("X-Experience-API-Version", "1.0.3"))

		mockClient.perform(post(agentsPath)
				.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(agentJson))
				.andDo(print())
				.andExpect(status().isOk)
//				.andExpect(content().json(expectedAgentJson))
	}

	@Test
	fun postGetActivity() {
//		val activityID: String = "http://example.adlnet.gov/xapi/example/simplestatement"
        val activityID: String = "12345"
		val activityIDJson: String = "{\"activityId\":\""+activityID+"\"}"
		val expectedActivityJson: String = "{\"id\":\""+activityID+"\","+
				"\"name\": {\"en-US\":\"simple statement\" }"+
				"},\"description\": {},"+
				"\"moreInfo\":\"\",\"interactionType\":null,"+
				"\"correctResponsesPattern\":[],"+
				"\"choices\":[],\"scale\":[],"+
				"\"source\":[],\"target\":[],\"steps\":[],"+
				"\"extensions\": {}}"
		val statementJson: String = "{\"id\":\"\","+
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
				"\"extensions\":{}}}"
		mockClient.perform(
				post(statementsPath)
						.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
						.header(Constants.XAPI_VERSION_HEADER, "1.0.3")
						.contentType(MediaType.APPLICATION_JSON)
						.content(statementJson))
				.andDo(print())
				.andExpect(status().isOk)
				.andExpect(header().string("X-Experience-API-Version", "1.0.3"))

		mockClient.perform(post(activityPath)
				.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(activityIDJson))
				.andDo(print())
				.andExpect(status().isOk)
//				.andExpect(content().json(expectedActivityJson))
	}

	@Test
	fun getXapiHeaderBasicAuth() {
		mockClient.perform(get(statementsPath)
			.header("Authorization", "Basic " + createBasicAuthHash(userName, password))
            .accept(MediaType.APPLICATION_JSON))
        	.andExpect(header().string("X-Experience-API-Version", "1.0.3")).andReturn()
	}

    private fun createBasicAuthHash(userName : String, password: String) : String {
        return String(Base64.encodeBase64((userName+":"+password).toByteArray()))
    }
}