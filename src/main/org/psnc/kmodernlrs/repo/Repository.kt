package org.psnc.kmodernlrs.repo

interface Repository {
	fun getAll() : Collection<*>
	fun get(key: Int) : Any
	fun add(obj: Any)
}