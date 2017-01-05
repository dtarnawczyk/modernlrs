package org.psnc.kmodernlrs.mongo

import java.lang.reflect.Field

import org.psnc.kmodernlrs.mongo.CascadeSave
import org.psnc.kmodernlrs.mongo.Activity
import org.psnc.kmodernlrs.models.XapiObject

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.util.ReflectionUtils
import org.springframework.util.ReflectionUtils.FieldCallback
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class CascadeCallback(var source: Any?, mongoOperations: MongoOperations) : ReflectionUtils.FieldCallback {

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
                    val definition: org.psnc.kmodernlrs.models.Activity?
                                = ReflectionUtils.getField(definitionField, fieldValue)
                                as? org.psnc.kmodernlrs.models.Activity
                    if(definition != null) {
                        val idField: Field = ReflectionUtils.findField(XapiObject::class.java, "id")
                        ReflectionUtils.makeAccessible(idField)
                        val idx: String? = ReflectionUtils.getField(idField, fieldValue) as? String

                        definition.id = idx

                        mongoTemplate?.save(definition)
                    }
                }
            }
        }
    }
}