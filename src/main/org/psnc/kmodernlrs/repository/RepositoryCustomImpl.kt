package org.psnc.kmodernlrs.repository

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.psnc.kmodernlrs.repository.RepositoryCustom
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria

/**
 * Operations on MongoDB using MongoTemplate. Can work as a replacement to MongoRepository.
 */
@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="mongodb")
@Repository
open class RepositoryCustomImpl : RepositoryCustom {

    @Autowired lateinit var mongoTemplate : MongoOperations

    override fun <T> create(entity: T) {
        mongoTemplate.insert(entity)
    }
    override fun <T> createList(entities: MutableList<T>) {
        mongoTemplate.insertAll(entities)
    }
    override fun <T> update(id: T, claz: Class<*>) {
        mongoTemplate.save(mongoTemplate.findOne(Query.query(Criteria.where("id").`is`(id)), claz))
    }
//    override fun <T> updateList(entities: MutableList<T>) {
//    }
    override fun <T> findById(id: T, claz: Class<*>) : Any? {
        return mongoTemplate.findOne(Query.query(Criteria.where("id").`is`(id)), claz)
    }
    override fun <T> deleteById(id: T, claz: Class<*>) {
        mongoTemplate.remove(Query.query(Criteria.where("id").`is`(id)), claz)
    }
//    override fun <T> delete(entity: T) {
//        mongoTemplate.remove(entity)
//    }
//    fun <T> delete(entities: MutableList<T>)
//    fun deleteAll(claz: Class<*>)
    override fun findAll(claz: Class<*>) : List<Any>? {
        return mongoTemplate.findAll(claz)
    }

//    fun <T> findAll(ids: MutableList<T>, claz: Class<*>): List<Any>?
    override fun getCount(claz: Class<*>) : Long {
        return mongoTemplate.findAll(claz).size.toLong()
    }

    override fun <T> exists(id: T, claz: Class<*>) : Boolean {
        return mongoTemplate.exists(Query.query(Criteria.where("id").`is`(id)), claz)
    }
}