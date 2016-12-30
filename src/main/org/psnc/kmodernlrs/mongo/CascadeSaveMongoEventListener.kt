package org.psnc.kmodernlrs.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.util.ReflectionUtils

class CascadeSaveMongoEventListener : AbstractMongoEventListener<Any>() {

    @Autowired
    lateinit var mongoOperations: MongoOperations

    override fun onBeforeConvert(source: Any) {
        ReflectionUtils.doWithFields(source.javaClass, CascadeCallback(source, mongoOperations))
    }
}