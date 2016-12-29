package org.psnc.kmodernlrs.services

import org.psnc.kmodernlrs.models.Statement

interface StatementService {

    fun createStatement(statement: Statement) : Boolean
    fun getStatement(id: String) : Statement?
    fun deleteStatement(id: String)
//    fun getAll() : List<Statement>?
    fun getCount() : Long
    fun exists(statement: Statement) : Boolean

}