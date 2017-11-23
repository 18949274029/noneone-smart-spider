package com.noneone.api.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping("login")
	@ResponseBody
	public String getLogin() {
		return "login";
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
