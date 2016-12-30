package org.psnc.kmodernlrs.mongo

import java.lang.reflect.Field

import org.springframework.data.annotation.Id
import org.springframework.util.ReflectionUtils

class FieldCallback : ReflectionUtils.FieldCallback {
    var idFound: Boolean = false

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)

        if (field.isAnnotationPresent(Id::class.java)) {
            idFound = true
        }
    }

    fun isIdFound(): Boolean = idFound
}