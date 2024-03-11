package com.example.demo.config;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class MyWebServerCustomizer implements WebServerFactoryCustomizer<WebServerFactory> 
{

	@Override
	public void customize(WebServerFactory factory) 
	{
		 if (factory instanceof TomcatServletWebServerFactory) {
	            TomcatServletWebServerFactory tomcatFactory = (TomcatServletWebServerFactory) factory;
	            tomcatFactory.addContextCustomizers(context -> context.setCookieProcessor(new Rfc6265CookieProcessor()));
	        }
	}

}
