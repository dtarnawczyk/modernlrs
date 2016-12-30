package org.psnc.kmodernlrs.apps

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import com.mongodb.Mongo
import com.mongodb.MongoClient
//import org.springframework.data.authentication.UserCredentials
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.authentication.UserCredentials
import org.springframework.data.mongodb.core.SimpleMongoDbFactory

@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="mongodb")
@Configuration
@EnableMongoRepositories(basePackages = arrayOf("org.psnc.kmodernlrs.repository"))
open class MongoDBConfig : AbstractMongoConfiguration() {

    @Value("&{database.mongodb.database}")
    lateinit var database:String

    @Value("&{database.mongodb.host}")
    lateinit var host:String

    @Value("&{database.mongodb.port}")
    lateinit var port:String

    override fun getDatabaseName(): String {
        return database
    }

    override fun mongo(): Mongo {
        return MongoClient(host, port.toInt())
    }

    @Bean
    override fun mongoTemplate(): MongoTemplate {
//        val userCredentials = UserCredentials(user, password)
//        return MongoTemplate(mongo(), database, userCredentials)
        return MongoTemplate(mongo(), database)
    }

    override fun getMappingBasePackage(): String {
        return "org.psnc.kmodernlrs.models"
    }
}