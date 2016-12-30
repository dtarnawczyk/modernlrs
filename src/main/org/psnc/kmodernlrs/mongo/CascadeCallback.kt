package org.psnc.kmodernlrs.mongo

import java.lang.reflect.Field

import org.psnc.kmodernlrs.mongo.CascadeSave
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.util.ReflectionUtils
import org.springframework.util.ReflectionUtils.FieldCallback

class CascadeCallback(var source: Any?, mongoOperations: MongoOperations) : ReflectionUtils.FieldCallback {
    var mongoOperations: MongoOperations? = null

    init {
        this.mongoOperations = mongoOperations
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)

        if (field.isAnnotationPresent(DBRef::class.java) && field.isAnnotationPresent(CascadeSave::class.java)) {
            val fieldValue = field.get(source)

            if (fieldValue != null) {
                val callback = FieldCallback()

                ReflectionUtils.doWithFields(fieldValue.javaClass, callback)

                mongoOperations?.save(fieldValue)
            }
        }

    }
}