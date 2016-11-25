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
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.assertj.core.api.Assertions.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@JsonTest
open class StatementsTest {
	
	@Autowired lateinit var json: GsonTester<Statement>
	
	val log = LoggerFactory.getLogger(StatementsTest::class.java)
	lateinit var statement: Statement
				
	@Before
	fun initialize() {
		statement = Statement("1", "1.0")
		var gson:Gson = GsonBuilder().create();
		GsonTester.initFields(this, gson);
	}
	
	@Test
	fun testJson() {
		log.debug(">>>>> json.write(statement) -> "+ json.write(statement))
		assertThat(json.write(statement)).isEqualToJson("{ \"id\": \"1\", \"version\": \"1.0\" }")
	}
	
}