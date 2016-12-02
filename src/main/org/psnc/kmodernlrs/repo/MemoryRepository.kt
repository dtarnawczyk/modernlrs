package org.psnc.kmodernlrs.repo

import org.psnc.kmodernlrs.models.*
import org.springframework.stereotype.Component

@Component
class MemoryRepository : Repository {
	
	private var store: MutableList<Statement> = mutableListOf<Statement>()
	
	override fun getAll() : Collection<*> {
		return store.toList()
	}
	
	override fun get(key: Int) : Any {
		return store.get(key)
	}
	
	override fun add(obj: Any) {
		store.add(obj as Statement)
	}
	
}