package model.spring;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.XmlViewResolver;

import misc.DemoHandlerInterceptor;

@Configuration
@ComponentScan(basePackages={"controller"})
@EnableWebMvc
public class SpringMVCJavaConfiguration implements WebMvcConfigurer {
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setBasename("i18n.message");
		return rbms;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
				new DemoHandlerInterceptor()).addPathPatterns("/secure/*");
	}
	
	@Autowired
	private ServletContext applicaton;
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ServletContextResource resource = 
				new ServletContextResource(applicaton, "/WEB-INF/views.xml");
		XmlViewResolver viewResolver = new XmlViewResolver();
		viewResolver.setLocation(resource);
		viewResolver.setOrder(10);
				
		registry.viewResolver(viewResolver);
	}
}
