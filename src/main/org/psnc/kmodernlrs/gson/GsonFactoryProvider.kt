package org.psnc.kmodernlrs.gson

import com.google.gson.Gson

interface GsonFactoryProvider {
	fun gsonFactory(): Gson
}