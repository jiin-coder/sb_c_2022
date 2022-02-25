package com.kja.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kja.exam.demo.intereceptor.BeforeActionInterceptor;
import com.kja.exam.demo.intereceptor.NeedLoginInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// NeedLoginInterceptor 인터셉터 불러오기
		@Autowired
		NeedLoginInterceptor needLoginInterceptor;

	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
				.excludePathPatterns("/error");
		
		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/usr/article/write")
		.addPathPatterns("/usr/article/doWrite").addPathPatterns("/usr/article/modify")
		.addPathPatterns("/usr/article/doModify").addPathPatterns("/usr/article/doDelete");
	}
}
