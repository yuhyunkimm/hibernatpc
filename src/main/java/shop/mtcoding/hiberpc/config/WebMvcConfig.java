package shop.mtcoding.hiberpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import shop.mtcoding.hiberpc.config.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // WebMvcConfigurer : solid isp를 지키지 않아서
    // WebMvcConfigurerAdapter

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/s/*"); // s(secuir)로 시작하면 인증이 필요하다

    }

}
