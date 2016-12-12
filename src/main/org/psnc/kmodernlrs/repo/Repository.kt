package org.psnc.kmodernlrs.repo

interface Repository<T> {
	fun getAll() : Collection<*>
	fun get(key: String) : T?
	fun add(key: String, obj: T)
}