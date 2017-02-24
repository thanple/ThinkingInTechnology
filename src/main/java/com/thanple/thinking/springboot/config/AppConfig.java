package com.thanple.thinking.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Thanple on 2017/2/24.
 */

@Configuration  //配置
@EnableScheduling //开启自动Schedule
public class AppConfig {

    @Autowired
    private MyConfig myConfig;  //这里的IDEA产生的警告可以忽略

    //MVC的拦截器
    @Bean
    public WebMvcConfigurer mvcConfigurer() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptorAdapter() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        if(!myConfig.getAPI_ON_OFF()){
                            response.getOutputStream().print("API Service is closed");
                            return false;
                        }
                        return true;
                    }
                });
            }

            //Resource静态文件配置
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                super.addResourceHandlers(registry);
            }
        };
    }


    //404 505拦截
    @Bean
    public ErrorPageRegistrar errorPageRegistrar(){
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
            }
        };
    }

}
