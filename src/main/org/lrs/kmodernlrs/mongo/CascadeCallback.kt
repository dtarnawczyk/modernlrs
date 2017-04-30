package org.lrs.kmodernlrs.mongo

import org.lrs.kmodernlrs.models.XapiObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.util.ReflectionUtils
import org.springframework.util.ReflectionUtils.FieldCallback
import java.lang.reflect.Field

class CascadeCallback(var source: Any?, mongoOperations: MongoOperations) : FieldCallback {

    val log: Logger = LoggerFactory.getLogger(CascadeCallback::class.java)

    private var mongoTemplate : MongoOperations?
    init {
        this.mongoTemplate = mongoOperations
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)
        val fieldValue = field.get(source)
        if (field.isAnnotationPresent(DBRef::class.java) && field.isAnnotationPresent(CascadeSave::class.java)) {
            if (fieldValue != null) {
                val callback = FieldCallback()
                ReflectionUtils.doWithFields(field.javaClass, callback)
                mongoTemplate?.save(fieldValue)
            }
        }

        if(field.isAnnotationPresent(Activity::class.java) ) {
            if (fieldValue != null) {
                val objectTypeField: Field = ReflectionUtils.findField(XapiObject::class.java, "objectType")
                ReflectionUtils.makeAccessible(objectTypeField)
                val objectType: String? = ReflectionUtils.getField(objectTypeField, fieldValue) as String?
                if ((objectType != null) && (objectType.equals("activity", true))) {
                    val definitionField: Field = ReflectionUtils.findField(XapiObject::class.java, "definition")
                    ReflectionUtils.makeAccessible(definitionField)
                    val definition: org.lrs.kmodernlrs.models.Activity?
                                = ReflectionUtils.getField(definitionField, fieldValue)
                                as? org.lrs.kmodernlrs.models.Activity
                    if(definition != null) {
                        val idField: Field = ReflectionUtils.findField(XapiObject::class.java, "id")
                        ReflectionUtils.makeAccessible(idField)
                        val idx: String = ReflectionUtils.getField(idField, fieldValue) as String

                        definition.id = idx

                        mongoTemplate?.save(definition)
                    }
                }
            }
        }
    }
}