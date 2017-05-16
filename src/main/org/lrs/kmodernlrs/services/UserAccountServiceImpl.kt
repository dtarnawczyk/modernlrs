package org.lrs.kmodernlrs.services

import org.lrs.kmodernlrs.domain.Entity
import org.lrs.kmodernlrs.domain.repository.RepositoryCustom
import org.lrs.kmodernlrs.controllers.security.UserAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class UserAccountServiceImpl : UserAccountService {

    val log: Logger = LoggerFactory.getLogger(UserAccountServiceImpl::class.java)

    @Autowired
    lateinit var repoCustom: RepositoryCustom<Entity>

    override fun createUserAccount(user: UserAccount) {
        repoCustom.create(user)
    }

    override fun updateUserAccount(user: UserAccount) {
        repoCustom.update(user, UserAccount::class.java)
    }

    override fun getUserAccountByUsername(username: String) : UserAccount? {
        val attributes = hashMapOf("name" to username)
        return repoCustom.findByAttrs(attributes, UserAccount::class.java) as UserAccount
    }

    override fun getAllUserAccounts(): List<UserAccount>? {
        val userAccounts: List<UserAccount>?
            = repoCustom.findAll(UserAccount::class.java)?.filterIsInstance<UserAccount>()
        return userAccounts
    }

    override fun deleteUserAccount(user: UserAccount) : Boolean {
        repoCustom.deleteById(user.id, UserAccount::class.java)
        return !(repoCustom.exists(user.id, UserAccount::class.java))
    }

    override fun exists(user: UserAccount): Boolean
            = repoCustom.exists(user.id, UserAccount::class.java)

    override fun getUserAccountCount() : Long
            = repoCustom.getCount(UserAccount::class.java)

}