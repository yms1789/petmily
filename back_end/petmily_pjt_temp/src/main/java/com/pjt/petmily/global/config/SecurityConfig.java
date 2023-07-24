//package com.pjt.petmily.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .formLogin().disable()//1 - formLogin 인증방법 비활성화
//                .httpBasic().disable()//2 - httpBasic 인증방법 비활성화(특정 리소스에 접근할 때 username과 password 물어봄)
//                .csrf().disable()   // csrf 보안 사용 X
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안하므로 STATELESS
//
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login", "/signUp","/").permitAll()
//                .anyRequest().authenticated();
//
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//}