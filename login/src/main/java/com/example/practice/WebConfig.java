package com.example.practice;

import com.example.practice.web.login.LoginCheckFilter;
import com.example.practice.web.login.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns("/", "/item/{itemId}", "/file/{fileName}", "/add", "/login", "/logout",
						"/css/**", "/*.ico", "/error");
	}

	//@Bean
	FilterRegistrationBean loginCheckFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LoginCheckFilter());
		//filterRegistrationBean.setOrder(1);
		//filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}
