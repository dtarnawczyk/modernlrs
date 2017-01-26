package org.lrs.kmodernlrs.repository

//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.lrs.kmodernlrs.models.Statement
import org.springframework.data.mongodb.repository.MongoRepository

//import org.springframework.stereotype.Repository

/**
 * Operations on MongoDB using MongoRepository. Can work as a replacement to MongoTemplate.
 */

//@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="mongodb")
//@Repository
interface StatementRepository : MongoRepository<Statement, String>