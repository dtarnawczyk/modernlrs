package org.psnc.kmodernlrs.repo

import org.psnc.kmodernlrs.models.*
import org.springframework.stereotype.Component

@Component
class MemoryRepository : Repository<Statement> {
	
	private var store: MutableMap<String, Statement> = mutableMapOf<String, Statement>()
	
	override fun getAll() : Collection<*> {
		return store.toList()
	}
	
	override fun get(key: String) : Statement? {
		return store.get(key)
	}
	
	override fun add(key: String, obj: Statement) {
		store.put(key, obj)
	}
	
}