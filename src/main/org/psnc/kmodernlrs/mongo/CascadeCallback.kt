package org.psnc.kmodernlrs.mongo

import java.lang.reflect.Field

import org.psnc.kmodernlrs.mongo.CascadeSave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.util.ReflectionUtils
import org.springframework.util.ReflectionUtils.FieldCallback
import org.slf4j.LoggerFactory

class CascadeCallback(var source: Any?, mongoOperations: MongoOperations) : ReflectionUtils.FieldCallback {

    val log = LoggerFactory.getLogger(CascadeCallback::class.java)

    private var mongoTemplate : MongoOperations?
    init {
        this.mongoTemplate = mongoOperations
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)

        if (field.isAnnotationPresent(DBRef::class.java) && field.isAnnotationPresent(CascadeSave::class.java)) {
            val fieldValue = field.get(source)

            if (fieldValue != null) {
                val callback = FieldCallback()

                ReflectionUtils.doWithFields(fieldValue.javaClass, callback)
                log.debug(">>>>> Saving: "+ fieldValue)
                mongoTemplate?.save(fieldValue)
            }
        }

    }

    companion object {
    }
}