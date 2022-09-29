package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("redirect:/login?logout");
        registry.addViewController("/viewUserPage").setViewName("viewUserPage");
        registry.addViewController("/adminPage").setViewName("adminPage");
    }
}
