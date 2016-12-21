package org.psnc.kmodernlrs.repo

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.stereotype.Repository
import java.util.UUID

@ConditionalOnProperty(name=arrayOf("database.type"), havingValue="cassandra")
@Repository
open class CassandraDb : org.psnc.kmodernlrs.repo.Repository {

    @Autowired
    lateinit var cassandraTemplate: CassandraOperations

    override fun <T> create(entity: T): T = cassandraTemplate.insert(entity)

    override fun <T> createList(entities: MutableList<T>) : MutableList<T> = cassandraTemplate.insert(entities)

    override fun <T> update(entity: T): T = cassandraTemplate.update(entity)

    override fun <T> updateList(entities: MutableList<T>) {
        cassandraTemplate.update(entities)
    }

    override fun <T> update(entity: T, claz: Class<*>): T = cassandraTemplate.update(entity)

    override fun <T> findById(id: T, claz: Class<*>): Any? = cassandraTemplate.selectOneById(claz, id)

    override fun <T> deleteById(id: T, claz: Class<*>){
        cassandraTemplate.deleteById(claz, id)
    }

    override fun <T> delete(entity: T){
        cassandraTemplate.delete(entity)
    }

    override fun <T> delete(entities: MutableList<T>) {
        cassandraTemplate.delete(entities)
    }

    override fun deleteAll(claz: Class<*>) {
        cassandraTemplate.deleteAll(claz)
    }

    override fun findAll(claz: Class<*>): List<Any>? = cassandraTemplate.selectAll(claz)

    override fun <T> findAll(ids: MutableList<T>, claz: Class<*>): List<Any>? = cassandraTemplate.selectBySimpleIds(claz, ids)

    override fun getCount(claz: Class<*>): Long = cassandraTemplate.count(claz)

    override fun <T> exists(id: T, claz: Class<*>): Boolean = cassandraTemplate.exists(claz, id)
}