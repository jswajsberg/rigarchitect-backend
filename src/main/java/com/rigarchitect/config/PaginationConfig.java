package com.rigarchitect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configuration for pagination defaults and behavior
 * Sets up consistent pagination parameters across the application
 */
@Configuration
@EnableSpringDataWebSupport
public class PaginationConfig implements WebMvcConfigurer {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setPageParameterName("page");
        resolver.setSizeParameterName("size");
        resolver.setOneIndexedParameters(false); // 0-based pagination
        resolver.setMaxPageSize(MAX_PAGE_SIZE);
        resolver.setFallbackPageable(PageRequest.of(0, DEFAULT_PAGE_SIZE));
        argumentResolvers.add(resolver);
    }
}