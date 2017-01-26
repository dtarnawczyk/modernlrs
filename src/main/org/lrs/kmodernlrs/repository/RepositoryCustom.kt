package org.lrs.kmodernlrs.repository

interface RepositoryCustom {

    fun <T> create(entity: T)
    fun <T> createList(entities: MutableList<T>)
    fun <T> update(id: T, claz: Class<*>)
//    fun <T> updateList(entities: MutableList<T>)
    fun <T> findById(id: T, claz: Class<*>) : Any?
    fun fimdByAttrs(attrs: Map<String, String>, claz: Class<*>) : Any?
    fun <T> deleteById(id: T, claz: Class<*>)
//    fun <T> delete(entities: MutableList<T>)
//    fun deleteAll(claz: Class<*>)
    fun findAll(claz: Class<*>) : List<Any>?
    fun find10(claz: Class<*>, skip: Int) : List<Any>?
    fun find20(claz: Class<*>, skip: Int) : List<Any>?
    fun find50(claz: Class<*>, skip: Int) : List<Any>?
    fun findAllLimitSkip(claz: Class<*>, limit: Int, skip: Int) : List<Any>?
    fun getCount(claz: Class<*>): Long
    fun <T> exists(id: T, claz: Class<*>): Boolean
}