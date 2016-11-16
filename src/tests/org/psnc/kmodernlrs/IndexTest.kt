package org.psnc.kmodernlrs

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
open class IndexTest {
	
	@Autowired
	lateinit var restTemplate : TestRestTemplate
	
	@Test		
	fun indexLoad() {
		var entity : ResponseEntity<String> = this.restTemplate.getForEntity("/", String::class.java)
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}