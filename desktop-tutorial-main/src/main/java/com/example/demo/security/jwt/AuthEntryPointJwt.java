package com.example.demo.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error : {}",authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Error : Unauthorized");
	}
	/*- HttpServletResponse.SC_UNAUTHORIZED is the 401 status code . It indicates that the request requires HTTP authentication
	 * - We've already built all things for Spring Security */

}
