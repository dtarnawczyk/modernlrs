package org.psnc.kmodernlrs.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.repository.StatementRepository
import org.psnc.kmodernlrs.repository.RepositoryCustomImpl
import org.psnc.kmodernlrs.models.Statement

/**
 * Can use the StatementRepository as well as the RepositoryCustomImpl, depends which one better fits.
 */
@Service
open class StatementServiceImpl : StatementService {

    @Autowired
    lateinit var statementRepo: StatementRepository

    @Autowired
    lateinit var repoCustom: RepositoryCustomImpl

    override fun createStatement(statement: Statement) : Boolean {
//        return repo.insert(statement, Statement::class.java )
        // OR
        repoCustom.create(statement)
        // TODO: check if exist
        return true
    }

    override fun getStatement(id: String) : Statement? {
        return repoCustom.findById(id, Statement::class.java) as Statement
    }

    override fun deleteStatement(id: String) {
        repoCustom.deleteById(id, Statement::class.java)
    }

    override fun getAll() : List<Statement>? {
        return repoCustom.findAll(Statement::class.java) as List<Statement>?
    }

    override fun getCount() : Long {
        return repoCustom.getCount(Statement::class.java)
    }

    override fun exists(statement: Statement) : Boolean {
        return repoCustom.exists(statement.id, Statement::class.java)
    }

}