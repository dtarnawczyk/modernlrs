package org.psnc.kmodernlrs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Test
import org.springframework.test.context.junit4.SpringRunner 
import org.psnc.kmodernlrs.Application
import org.psnc.kmodernlrs.models.*
import org.springframework.boot.test.json.GsonTester
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertNotNull
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.Calendar
import java.util.Date
import java.sql.Timestamp

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(org.psnc.kmodernlrs.Application::class))
@JsonTest
open class StatementsTest {
	
	@Autowired lateinit var jsonTester: GsonTester<Statement>
	
	val log = LoggerFactory.getLogger(StatementsTest::class.java)
	lateinit var statement: Statement
				
	@Before
	fun initialize() {
		var actor: Actor = Actor()
		actor.mbox = "mailto:test@server.com"
		var verb: Verb = Verb("http://example.com/xapi/verbs#defenestrated", hashMapOf("pl" to "test"))
		var obj: XapiObject = XapiObject("testid")
		statement = Statement("1", actor, verb, obj, Timestamp(Calendar.getInstance().getTime().getTime()), "1.0")
		var gson:Gson = GsonBuilder().create()
		GsonTester.initFields(this, gson)
	}
	
	@Test
	fun createJson() {
		log.debug(">>> jsonTester.write(statement) -> "+ jsonTester.write(statement))
		assertNotNull(jsonTester.write(statement))
	}
	
	@Test
	fun testJsonActor() {
		var json: String = "{\"id\": \"12345678-1234-5678-1234-567812345678\","+
		  "\"actor\":{"+
		  "\"mbox\":\"mailto:xapi@adlnet.gov\","+
		  "\"name\":\"test actor\","+
		  "\"objectType\": \"Group\","+
        "\"member\": ["+
    	 "{\"objectType\": \"Agent\","+
    	  	"\"name\": \"member one\""+
      	"}],\"account\": {"+
        	"\"homePage\": \"http://www.example.pl\","+
        	"\"name\": \"1625378\"}},"+
        "\"verb\":{"+
        "\"id\":\"http://adlnet.gov/expapi/verbs/created\","+
        "\"display\":{\"en-US\":\"created\""+
        "}},\"object\":{\"id\":\"http://example.adlnet.gov/xapi/example/activity\"}}"
		log.debug(">>>> json -> "+ json)
		var statement: Statement = jsonTester.parseObject(json)
		assertNotNull(statement.id)
		assertNotNull(statement.actor.mbox)
		assertNotNull(statement.actor.name)
		assertNotNull(statement.actor.objectType)
		assertNotNull(statement.actor.member.toTypedArray().get(0).objectType)
		assertNotNull(statement.actor.member.toTypedArray().get(0).name)
		assertNotNull(statement.actor.account?.name)
		assertNotNull(statement.actor.account?.homePage)
		assertNotNull(statement.verb.id)
		assertNotNull(statement.verb.display)
		assertNotNull(statement.xapiObj.id)
	}
}