package org.psnc.kmodernlrs.controllers

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
open class IndexController {
	
	@RequestMapping(value="/home")
	fun index() : String {
		return "index"
	}
}