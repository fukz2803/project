package com.foodei.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceCustom userDetailsServiceCustom;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceCustom)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/","/blogs","/category/**","/about","/contact",
                            "/dashboard/forgot-password",
                            "/dashboard/register").permitAll()
                    .antMatchers("/dashboard/profile/**").hasRole("MEMBER")
                    .antMatchers("/dashboard/blogs",
                            "/dashboard/admin/users",
                            "/dashboard/categories",
                            "/dashboard/categories/delete/").hasRole("ADMIN")
                    .antMatchers("/dashboard/my-blogs/**",
                            "/dashboard/blogs/create-blog",
                            "/dashboard/blogs/detail/**",
                            "/dashboard/blogs/delete/",
                            "/dashboard/blogs/edit/**").hasAnyRole("EDITOR","ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/dashboard/login")
                    .loginProcessingUrl("/login-custom")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/dashboard/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/web/**","/admin/**");
    }
}
