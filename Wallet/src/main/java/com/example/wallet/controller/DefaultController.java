package com.example.wallet.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class DefaultController implements ErrorController {

	@RequestMapping(value = "/error", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Link to swagger", notes = "Goto localhost:2004/swagger-ui.html", response = String.class)
	public String handleError() {
		return "Goto localhost:2004/swagger-ui.html";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
