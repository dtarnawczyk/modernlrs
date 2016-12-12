package org.psnc.kmodernlrs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Test
import org.apache.commons.codec.binary.Base64
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.*
import org.springframework.http.MediaType
import org.psnc.kmodernlrs.Application
import org.psnc.kmodernlrs.Constants
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpHeaders
import org.assertj.core.api.Assertions.assertThat

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(org.psnc.kmodernlrs.Application::class), webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
open class WebTest {
	
	@Autowired lateinit var mockClient: MockMvc
	
	@Value("&{auth.basic.username}")
	lateinit var userName:String
	
	@Value("&{auth.basic.password}")
	lateinit var password:String
	
//	val log = LoggerFactory.getLogger(WebTest::class.java)
	
	var statementsPath: String = "/v1/xAPI/statements"
	
	@Test
	fun basicAuthTest() {
		var basic: String = userName+":"+password
		var encodedBytes = Base64.encodeBase64(basic.toByteArray())
		mockClient.perform(get(statementsPath)
			.header("Authorization", "Basic " + String(encodedBytes))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
	}
	
	@Test
	fun postGetStatementIdTest() {
		var basic: String = userName+":"+password
		var encodedBytes = Base64.encodeBase64(basic.toByteArray())
		var statementId = "1234-56789-12324"
		var statementJson: String = "{\"id\":\""+statementId+"\","+
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
				.header("Authorization", "Basic " + String(encodedBytes))
				.header(Constants.XAPI_VERSION_HEADER, "1.0.3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(statementJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string("X-Experience-API-Version", "1.0.3"))
		
		mockClient.perform(get(statementsPath+"/"+statementId)
			.header("Authorization", "Basic " + String(encodedBytes))
            .accept(MediaType.APPLICATION_JSON))
			.andDo(print())
            .andExpect(status().isOk())
	}
	
	@Test
	fun getXapiHeaderBasicAuth() {
		var basic: String = userName+":"+password
		var encodedBytes = Base64.encodeBase64(basic.toByteArray())
		mockClient.perform(get(statementsPath)
			.header("Authorization", "Basic " + String(encodedBytes))
            .accept(MediaType.APPLICATION_JSON))
        	.andExpect(header().string("X-Experience-API-Version", "1.0.3")).andReturn()
	}
}