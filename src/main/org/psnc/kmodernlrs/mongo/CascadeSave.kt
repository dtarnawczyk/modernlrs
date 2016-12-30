package org.psnc.kmodernlrs.mongo

import kotlin.annotation.Retention
import kotlin.annotation.Target

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class CascadeSave