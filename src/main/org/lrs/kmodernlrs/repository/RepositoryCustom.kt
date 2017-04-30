package org.lrs.kmodernlrs.repository

interface RepositoryCustom <Entity> {

    fun create(entity: Entity)
    fun createList(entities: MutableList<Entity>)
    fun update(entity: Entity, claz: Class<*>)
//    fun <T> updateList(entities: MutableList<T>)
    fun findById(id: String, claz: Class<*>) : Any?
    fun findByAttrs(attrs: Map<String, String>, claz: Class<*>) : Any?
    fun deleteById(id: String, claz: Class<*>)
//    fun <T> delete(entities: MutableList<T>)
//    fun deleteAll(claz: Class<*>)
    fun findAll(claz: Class<*>) : List<Any>?
    fun find10(claz: Class<*>, skip: Int) : List<Any>?
    fun find20(claz: Class<*>, skip: Int) : List<Any>?
    fun find50(claz: Class<*>, skip: Int) : List<Any>?
    fun findAllLimitSkip(claz: Class<*>, limit: Int, skip: Int) : List<Any>?
    fun getCount(claz: Class<*>): Long
    fun exists(id: String, claz: Class<*>): Boolean
}