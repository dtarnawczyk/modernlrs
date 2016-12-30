package org.psnc.kmodernlrs.repository

//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.psnc.kmodernlrs.models.Statement
import org.psnc.kmodernlrs.repository.RepositoryCustom
import org.springframework.data.mongodb.repository.MongoRepository
//import org.springframework.stereotype.Repository

/**
 * Operations on MongoDB using MongoRepository. Can work as a replacement to MongoTemplate.
 */

//@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="mongodb")
//@Repository
interface StatementRepository : MongoRepository<Statement, String> { }