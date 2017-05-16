package org.lrs.kmodernlrs.repository

import org.lrs.kmodernlrs.domain.Entity
import org.lrs.kmodernlrs.domain.repository.RepositoryCustom
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.stereotype.Repository

/**
 * Operations on MongoDB using MongoTemplate. Can work as a replacement to MongoRepository.
 */
@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="mongodb")
@Repository
open class RepositoryCustomImpl : RepositoryCustom<Entity> {

    val log: Logger = LoggerFactory.getLogger(RepositoryCustomImpl::class.java)

    @Autowired lateinit var mongoTemplate : MongoOperations

    override fun create(entity: Entity) {
        mongoTemplate.insert(entity)
    }
    override fun createList(entities: MutableList<Entity>) {
        mongoTemplate.insertAll(entities)
    }
    override fun update(entity: Entity, claz: Class<*>) {
        mongoTemplate.save(entity)
    }
    //    override fun <T> updateList(entities: MutableList<T>) {
//    }
    override fun findById(id: String, claz: Class<*>) : Any? =
            mongoTemplate.findOne(query(Criteria.where("id").`is`(id)), claz)

    override fun findByAttrs(attrs: Map<String, String>, claz: Class<*>) : Any?{
        val criterias = arrayListOf<Criteria>()
        for ((key, value) in attrs) {
            criterias.add(Criteria.where(key as String?).`is`(value))
        }
        //return new Criteria().orOperator(criterias.toArray(new Criteria[criterias.size()]));
        val criteria: Criteria = Criteria().orOperator(*criterias.toTypedArray())
        return mongoTemplate.findOne(query(criteria), claz)
    }

    override fun deleteById(id: String, claz: Class<*>) {
        mongoTemplate.remove(query(Criteria.where("id").`is`(id)), claz)
    }
    //    override fun <T> delete(entity: T) {
//        mongoTemplate.remove(entity)
//    }
//    fun <T> delete(entities: MutableList<T>)
//    fun deleteAll(claz: Class<*>)
    override fun findAll(claz: Class<*>) : List<Any>? = mongoTemplate.findAll(claz)

    override fun find10(claz: Class<*>, skip: Int) : List<Any>? {
        val query = Query()
        query.limit(10)
        query.skip(skip)
        return mongoTemplate.find(query, claz)
    }

    override fun find20(claz: Class<*>, skip: Int) : List<Any>? {
        val query = Query()
        query.limit(20)
        query.skip(skip)
        return mongoTemplate.find(query, claz)
    }

    override fun find50(claz: Class<*>, skip: Int) : List<Any>? {
        val query = Query()
        query.limit(50)
        query.skip(skip)
        return mongoTemplate.find(query, claz)
    }

    override fun findAllLimitSkip(claz: Class<*>, limit: Int, skip: Int) : List<Any>? {
        val query = Query()
        query.limit(limit)
        query.skip(skip)
        return mongoTemplate.find(query, claz)
    }

    //    fun <T> findAll(ids: MutableList<T>, claz: Class<*>): List<Any>?
    override fun getCount(claz: Class<*>) : Long = mongoTemplate.findAll(claz).size.toLong()

    override fun exists(id: String, claz: Class<*>) : Boolean = mongoTemplate.exists(query(Criteria.where("id").`is`(id)), claz)
}