package org.psnc.kmodernlrs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Test
import org.springframework.test.context.junit4.SpringRunner 
import org.psnc.kmodernlrs.Application
import org.psnc.kmodernlrs.models.Statement
import org.springframework.boot.test.json.GsonTester
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.assertj.core.api.Assertions.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
open class RestTest {
	
	@Autowired lateinit var template: TestRestTemplate
	
//	val log = LoggerFactory.getLogger(RestTest::class.java)	
	
	@Test
	fun getStatement() {
		var entity: ResponseEntity<String> = this.template.getForEntity("/xAPI/statements/", String::class.java)
		println(">>> BODY: "+ entity.body)
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}