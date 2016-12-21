package org.psnc.kmodernlrs.repo

import org.psnc.kmodernlrs.models.Statement

interface StatementRepository {

    fun createStatement(statement: Statement) : Statement?
    fun getStatement(id: String) : Statement?
    fun deleteStatement(id: String)
    fun getAll() : List<Statement>?
    fun getCount() : Long
    fun exists(id: Statement) : Boolean

}