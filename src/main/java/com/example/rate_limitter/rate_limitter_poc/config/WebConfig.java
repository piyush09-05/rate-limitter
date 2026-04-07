package com.example.rate_limitter.rate_limitter_poc.config;

import com.example.rate_limitter.rate_limitter_poc.interceptor.RateLimitInterceptorFixedWindow;
import com.example.rate_limitter.rate_limitter_poc.interceptor.RateLimitInterceptorSlidingWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitInterceptorFixedWindow rateLimitInterceptorFixedWindow;
    @Autowired
    private RateLimitInterceptorSlidingWindow rateLimitInterceptorSlidingWindow;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(rateLimitInterceptorSlidingWindow).addPathPatterns("/**");
    }


}
