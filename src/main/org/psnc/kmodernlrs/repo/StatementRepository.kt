package org.psnc.kmodernlrs.repo

import org.psnc.kmodernlrs.models.Statement
import org.springframework.data.repository.CrudRepository

interface StatementRepository : CrudRepository<Statement, Long> {
}