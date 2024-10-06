package br.edu.ifgoiano.ticket.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimiterConfig> rateLimiterFilter() {
        FilterRegistrationBean<RateLimiterConfig> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RateLimiterConfig());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
