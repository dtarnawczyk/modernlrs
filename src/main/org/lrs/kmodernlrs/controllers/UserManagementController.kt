package org.lrs.kmodernlrs.controllers

import org.lrs.kmodernlrs.controllers.security.UserAccount
import org.lrs.kmodernlrs.controllers.security.UserAccountProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.ws.rs.core.MediaType

@RestController
open class UserManagementController {

    val log: Logger = LoggerFactory.getLogger(UserManagementController::class.java)

    @Autowired lateinit var userAccountsProvider: UserAccountProvider

    @RequestMapping(path = arrayOf("/createNewUser"),
            method = arrayOf(RequestMethod.POST),
            consumes = arrayOf(MediaType.APPLICATION_JSON))
    fun createNewUser(@RequestBody user: UserAccount) : ResponseEntity<String> {
        user.createdTime = LocalDate.now()
        log.debug(">>>>> creating user: $user")
        val created: Boolean = userAccountsProvider.createUserAccount(user)
        if(created) {
            return ResponseEntity("User created successfully ", HttpStatus.CREATED)
        } else {
            return ResponseEntity("User creation ERROR !", HttpStatus.CONFLICT)
        }
    }

    @RequestMapping(path = arrayOf("/deleteUser"),
            method = arrayOf(RequestMethod.POST),
            consumes = arrayOf(MediaType.APPLICATION_JSON))
    fun deleteUser(@RequestBody user: UserAccount) : ResponseEntity<String> {
        user.createdTime = LocalDate.now()
        log.debug(">>>>> deleting user: $user")
        val deleted: Boolean = userAccountsProvider.deleteUserAccount(user)
        if(deleted) {
            return ResponseEntity("User deleted successfully ", HttpStatus.CREATED)
        } else {
            return ResponseEntity("User deleted ERROR !", HttpStatus.CONFLICT)
        }
    }

}
