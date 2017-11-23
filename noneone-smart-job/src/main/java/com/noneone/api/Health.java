package com.noneone.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class Health {
	
   public static final String SUCCESS_STRING = "ok";
   
   @RequestMapping("/health")
   @ResponseBody
	public String getHealth(){
		return SUCCESS_STRING;
	}
}
