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
@SpringBootTest(classes = arrayOf(Application::class))
@JsonTest
open class StatementsTest {
	
	@Autowired lateinit var jsonTester: GsonTester<Statement>
	
	val log = LoggerFactory.getLogger(StatementsTest::class.java)
	lateinit var statement: Statement
	
	var jsonWithActor: String = "{\"id\": \"12345678-1234-5678-1234-567812345678\","+
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
	
	var jsonWithActivityObject: String = "{\"id\": \"12345678-1234-5678-1234-567812345678\","+
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
        "}},\"object\":{"+
        "\"id\": \"http://www.example.co.uk/exampleactivity\","+
        "\"definition\": {"+
        "\"name\": { \"en-GB\": \"example activity\","+
        "\"en-US\": \"example activity\" },"+
        "\"description\": {\"en-GB\": \"An example of an activity\","+
        "\"en-US\": \"An example of an activity\" },"+
        "\"type\": \"http://www.example.co.uk/types/exampleactivitytype\""+
    	"},\"objectType\": \"Activity\"}}"
	
	var jsonWithSubstatementObject: String = "{\"id\": \"12345678-1234-5678-1234-567812345678\","+
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
        "}},\"object\":{\"objectType\": \"SubStatement\","+
		"\"actor\" : { \"objectType\": \"Agent\", \"mbox\":\"mailto:agent@example.com\" "+
		"},\"verb\" : { \"id\":\"http://example.com/confirmed\", \"display\":{"+
		"\"en\":\"confirmed\"} },\"object\": {\"objectType\":\"StatementRef\","+
		"\"id\" :\"9e13cefd-53d3-4eac-b5ed-2cf6693903bb\"}}}"
		
	var longStatement: String = "{\"id\": \"6690e6c9-3ef0-4ed3-8b37-7f3964730bee\","+
    "\"actor\": {\"name\": \"Team PB\",\"mbox\": \"mailto:teampb@example.com\","+
    "\"member\": [{\"name\": \"Andrew Downes\",\"account\": {"+
    "\"homePage\": \"http://www.example.com\",\"name\": \"13936749\"},"+
    "\"objectType\": \"Agent\"}, { \"name\": \"Toby Nichols\","+
    "\"openid\": \"http://toby.openid.example.org/\","+
    "\"objectType\": \"Agent\"},{\"name\": \"Ena Hills\","+
    "\"mbox_sha1sum\": \"ebd31e95054c018b10727ccffd2ef2ec3a016ee9\","+
    "\"objectType\": \"Agent\"}], \"objectType\": \"Group\"},\"verb\": {"+
    "\"id\": \"http://adlnet.gov/expapi/verbs/attended\", \"display\": {"+
    "\"en-GB\": \"attended\",\"en-US\": \"attended\"}}, \"result\": {"+
    //"\"extensions\": {\"http://example.com/profiles/meetings/resultextensions/minuteslocation\": \"X:/meetings/minutes/examplemeeting.one\"\"},"+
    "\"success\": true,\"completion\": true,\"response\": \"We agreed on some example actions.\","+
    "\"duration\": \"PT1H0M0S\"},\"context\": {"+
    "\"registration\": \"ec531277-b57b-4c15-8d91-d292c5b2b8f7\","+
    "\"contextActivities\": {\"parent\": [{\"id\": \"http://www.example.com/meetings/series/267\","+
    "\"objectType\": \"Activity\"}],\"category\": [{"+
    "\"id\": \"http://www.example.com/meetings/categories/teammeeting\","+
    "\"objectType\": \"Activity\",\"definition\": {\"name\": {"+
    "\"en\": \"team meeting\"},\"description\": {"+
    "\"en\": \"A category of meeting used for regular team meetings.\""+
    "},\"type\": \"http://example.com/expapi/activities/meetingcategory\""+
    "}}],\"other\": [{\"id\": \"http://www.example.com/meetings/occurances/34257\","+
    "\"objectType\": \"Activity\"}, {\"id\": \"http://www.example.com/meetings/occurances/3425567\","+
    "\"objectType\": \"Activity\"}]},\"instructor\" :{\"name\": \"Andrew Downes\","+
    "\"account\": { \"homePage\": \"http://www.example.com\",\"name\":\"13936749\""+
    "},\"objectType\": \"Agent\"},\"team\":{\"name\": \"Team PB\","+
    "\"mbox\": \"mailto:teampb@example.com\",\"objectType\": \"Group\""+
    "}, \"platform\" : \"Example virtual meeting software\",\"language\" : \"tlh\","+
    "\"statement\" : {\"objectType\":\"StatementRef\","+
    "\"id\":\"6690e6c9-3ef0-4ed3-8b37-7f3964730bee\" }},\"timestamp\": \"2013-05-18T05:32:34.804+00:00\","+
    "\"stored\": \"2013-05-18T05:32:34.804+00:00\","+
    "\"authority\": {\"account\": { \"homePage\": \"http://cloud.scorm.com/\","+
    "\"name\": \"anonymous\"},\"objectType\": \"Agent\"},\"version\": \"1.0.0\","+
    "\"object\": {\"id\": \"http://www.example.com/meetings/occurances/34534\",\"definition\": {"+
    "\"extensions\": {\"http://example.com/profiles/meetings/activitydefinitionextensions/room\": "+
	"{\"name\": \"Kilby\", \"id\" :\"http://example.com/rooms/342\"}"+
    "},\"name\": {\"en-GB\": \"example meeting\","+
    "\"en-US\": \"example meeting\"},\"description\": {"+
    "\"en-GB\": \"An example meeting that happened on a specific occasion with certain people present.\","+
    "\"en-US\": \"An example meeting that happened on a specific occasion with certain people present.\""+
    "},\"type\": \"http://adlnet.gov/expapi/activities/meeting\","+
    "\"moreInfo\": \"http://virtualmeeting.example.com/345256\""+
    "},\"objectType\": \"Activity\"}}"
	
	@Before
	fun initialize() {
		var actor: Actor = Actor()
		actor.mbox = "mailto:test@server.com"
		var verb: Verb = Verb("http://example.com/xapi/verbs#defenestrated", hashMapOf("pl" to "test"))
		var obj: XapiObject = XapiObject("testid", "Activity", null)
		statement = Statement("1", actor, verb, obj, null, null, null, Timestamp(Calendar.getInstance().getTime().getTime()).toString(), null, "1.0", emptyList())
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
		log.debug(">>>> json with actor -> "+ jsonWithActor)
		var statement: Statement = jsonTester.parseObject(jsonWithActor)
		assertNotNull(statement.id)
		assertNotNull(statement.actor?.mbox)
		assertNotNull(statement.actor?.name)
		assertNotNull(statement.actor?.objectType)
		assertNotNull(statement.actor?.member?.toTypedArray()?.get(0)!!.objectType)
		assertNotNull(statement.actor?.member?.toTypedArray()?.get(0)!!.name)
		assertNotNull(statement.actor?.account?.name)
		assertNotNull(statement.actor?.account?.homePage)
		assertNotNull(statement.verb?.id)
		assertNotNull(statement.verb?.display)
		assertNotNull(statement.xapiObj?.id)
	}

	@Test
	fun testJsonActivityObject() {
		log.debug(">>>> json with object as an activity -> "+ jsonWithActivityObject)
		var statement: Statement = jsonTester.parseObject(jsonWithActivityObject)
		assertNotNull(statement.id)
		assertNotNull(statement.xapiObj?.id)
		assertThat(statement.xapiObj?.objectType).isEqualTo("Activity")
		assertNotNull(statement.xapiObj?.definition)
		assertThat(statement.xapiObj?.definition?.name).isNotEmpty()
		assertThat(statement.xapiObj?.definition?.description).isNotEmpty()
		assertThat(statement.xapiObj?.definition?.type).isEqualTo("http://www.example.co.uk/types/exampleactivitytype")
	}

	@Test
	fun testJsonSubstatementObject() {
		log.debug(">>>> json with object as a substatement -> "+ jsonWithSubstatementObject)
		var statement: Statement = jsonTester.parseObject(jsonWithSubstatementObject)
		assertThat(statement.xapiObj?.objectType).isEqualTo("SubStatement")
		assertNotNull(statement.xapiObj?.actor)
		assertThat(statement.xapiObj?.actor?.objectType).isEqualTo("Agent")
		assertThat(statement.xapiObj?.actor?.mbox).isEqualTo("mailto:agent@example.com")
		assertNotNull(statement.xapiObj?.verb)
		assertThat(statement.xapiObj?.verb?.display).containsEntry("en","confirmed")
		assertThat(statement.xapiObj?.xapiObj?.objectType).isEqualTo("StatementRef")
		assertThat(statement.xapiObj?.xapiObj?.id).isEqualTo("9e13cefd-53d3-4eac-b5ed-2cf6693903bb")
	}

	@Test
	fun testLongJsonStatement() {
		log.debug(">>>> json long statement -> "+ longStatement)
		var statement: Statement = jsonTester.parseObject(longStatement)
		assertThat(statement.actor?.member).hasSize(3)
		assertThat(statement.verb?.id).isEqualTo("http://adlnet.gov/expapi/verbs/attended")
		assertThat(statement.result?.response).isEqualTo("We agreed on some example actions.")
		assertThat(statement.context?.registration).isEqualTo("ec531277-b57b-4c15-8d91-d292c5b2b8f7")
		assertThat(statement.context?.contextActivities?.parent?.first()?.objectType).isEqualTo("Activity")
		assertThat(statement.context?.contextActivities?.category?.first()?.definition?.description)
				.containsEntry("en", "A category of meeting used for regular team meetings.")
		assertThat(statement.context?.contextActivities?.other?.get(1)?.objectType).isEqualTo("Activity")
		assertThat(statement.context?.instructor?.name).isEqualTo("Andrew Downes")
		assertThat(statement.context?.instructor?.account?.name).isEqualTo("13936749")
		assertThat(statement.context?.team?.mbox).isEqualTo("mailto:teampb@example.com")
		assertThat(statement.context?.language).isEqualTo("tlh")
		assertThat(statement.context?.statement?.id).isEqualTo("6690e6c9-3ef0-4ed3-8b37-7f3964730bee")
        assertNotNull(statement.timestamp)
        assertNotNull(statement.stored)
		assertThat(statement.authority?.account?.homePage).isEqualTo("http://cloud.scorm.com/")
		assertThat(statement.version).isEqualTo("1.0.0")
		assertThat(statement.xapiObj?.definition?.extensions).containsKey("http://example.com/profiles/meetings/activitydefinitionextensions/room")
		assertThat(statement.xapiObj?.definition?.moreInfo).isEqualTo("http://virtualmeeting.example.com/345256")
	}
	
}