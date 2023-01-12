package com.onoprienko.onlineshop.security.configuration;

import com.onoprienko.onlineshop.security.entity.Role;
import com.onoprienko.onlineshop.service.impl.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final static String COOKIE_NAME = "user-token";
    private final DefaultUserService defaultUserService;

    @Value("${security.cookie.time-to-live}")
    private int cookieTimeToLive;

    @Value("${security.secret.key}")
    private String secret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().deleteCookies(COOKIE_NAME).logoutUrl("/logout")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login").permitAll()
                .usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/products")
                .and()
                .rememberMe()
                .alwaysRemember(true)
                .tokenValiditySeconds(cookieTimeToLive)
                .rememberMeCookieName(COOKIE_NAME)
                .key(secret)
                .and()
                .rememberMe().useSecureCookie(true).userDetailsService(defaultUserService)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/registration").permitAll()
                .requestMatchers("/product/add", "/product/edit", "product/remove").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers("/cart").hasAnyAuthority(Role.USER.name())
                .anyRequest().authenticated();
        return http.build();
    }


}