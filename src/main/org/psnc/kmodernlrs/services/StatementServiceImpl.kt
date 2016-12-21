package org.psnc.kmodernlrs.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.psnc.kmodernlrs.repo.StatementRepository
import org.psnc.kmodernlrs.models.Statement

@Service
open class StatementServiceImpl : StatementService {

    @Autowired
    lateinit var repo: StatementRepository

    override fun createStatement(statement: Statement) : Statement? = repo.createStatement(statement)

    override fun getStatement(id: String) : Statement? = repo.getStatement(id)

    override fun deleteStatement(id: String) = repo.deleteStatement(id)

    override fun getAll() : List<Statement>? = repo.getAll()

    override fun getCount() : Long = repo.getCount()

    override fun exists(id: Statement) : Boolean = repo.exists(id)

}