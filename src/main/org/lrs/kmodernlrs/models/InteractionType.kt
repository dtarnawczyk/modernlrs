package org.lrs.kmodernlrs.models

enum class InteractionType(var type: String) {
	TRUEFALSE("true-false"),
	CHOICE("choice"),
	FILLIN("fill-in"),
	LONGFILLIN("long-fill-in"),
	MATCHING("matching"),
	PERFORMANCE("performance"),
	SEQUENCING("sequencing"),
	LIKERT("likert"),
	NUMERIC("numeric"),
	OTHER("other")
}