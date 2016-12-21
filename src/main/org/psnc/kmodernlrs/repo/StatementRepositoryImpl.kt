package org.psnc.kmodernlrs.repo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.psnc.kmodernlrs.models.Statement

@Repository
open class StatementRepositoryImpl : StatementRepository {

    @Autowired
    lateinit var repo: org.psnc.kmodernlrs.repo.Repository

    override fun createStatement(statement: Statement) : Statement? {
        return repo.create(statement)
    }

    override fun getStatement(id: String) : Statement? {
        return repo.findById(id, Statement::class.java) as Statement?
    }

    override fun deleteStatement(id: String) {
        return repo.deleteById(id, Statement::class.java)
    }

    override fun getAll() : List<Statement>? {
        return repo.findAll(Statement::class.java) as List<Statement>?
    }

    override fun getCount() : Long {
        return repo.getCount(Statement::class.java)
    }

    override fun exists(id: Statement) : Boolean {
        return repo.exists(id, Statement::class.java)
    }
}