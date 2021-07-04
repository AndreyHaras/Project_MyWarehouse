package com.myprogect.mywarehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

@SpringBootApplication
public class MyWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWarehouseApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("templates/messages/messages_ru");
        messageSource.setDefaultEncoding("Windows-1251");
        return messageSource;
    }

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true)
    protected class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        DataSource dataSource;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("SELECT имя_пользователя AS username," +
                            " пароль_пользователя AS password, доступ AS enabled" +
                            " FROM пользователи WHERE имя_пользователя = ?")
                    .authoritiesByUsernameQuery("SELECT имя_пользователя AS username,\n" +
                            "       роль AS authority\n" +
                            "       FROM пользователи " +
                            "       WHERE имя_пользователя = ?");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/css/**").permitAll() //Не требуется аутонтификация
                    .antMatchers("/js/**").permitAll()
                    .anyRequest().authenticated() //Все запросы требуют аутонтификацию
                    .and()
                    .formLogin().permitAll()
                    .and()
                    .headers().frameOptions().disable()
                    .and()
                    .logout().permitAll()
                    .and()
                    .csrf().disable();
        }

        @Bean
        @SuppressWarnings({"unchecked", "deprecated"})
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }
}
