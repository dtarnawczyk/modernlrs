package org.lrs.kmodernlrs.services

import org.lrs.kmodernlrs.domain.Statement

interface StatementService {

    fun createStatement(statement: Statement) : Boolean
    fun getStatement(id: String) : Statement?
    fun deleteStatement(id: String)
    fun getAll() : List<Statement>?
    fun get10Statements(from: Int) : List<Statement>?
    fun get20Statements(from: Int) : List<Statement>?
    fun get50Statements(from: Int) : List<Statement>?
    fun getStatementsLimitFrom(limit: Int, from: Int) : List<Statement>?
    fun getCount() : Long
    fun exists(statement: Statement) : Boolean

}