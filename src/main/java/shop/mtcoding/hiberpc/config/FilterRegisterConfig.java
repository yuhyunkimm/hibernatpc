package shop.mtcoding.hiberpc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.hiberpc.config.filter.MyBlackListFilter;

@Configuration
public class FilterRegisterConfig {

    @Bean // return을 해주면 IOC에 저장을 시켜준다 내가 만든게 아닌 것은 Bean으로 해주는게 좋다
    public FilterRegistrationBean<?> blackListFilter() {
        FilterRegistrationBean<MyBlackListFilter> registraion = new FilterRegistrationBean<>();
        registraion.setFilter(new MyBlackListFilter());
        registraion.addUrlPatterns("/filter");
        registraion.setOrder(1);
        return registraion; // registraion 이 객체를 IOC에 띄우면 등록이 된다

    }
}
