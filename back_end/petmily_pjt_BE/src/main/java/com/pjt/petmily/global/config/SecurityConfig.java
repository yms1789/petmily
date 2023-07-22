//package com.pjt.petmily.global.config;
//
//import lombok.RequiredArgsConstructor;
//import com.pjt.petmily.domain.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
//public class SecurityConfig {
//
//    private final UserDetailsService userService;
//
//    // 1. 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성 ( 이 메서드에서 인증/인가 및 로그인, 로그아웃 관련 설정할 수 있음)
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests() // 3. 인증, 인가 설정 (특정 경로에 대한 엑세스 설정)
//                .requestMatchers("/login", "/signup", "/user").permitAll()// 특정 요청과 일치하는 url에 대한 엑세스 설정. 누구나 접근이 가능하게 설정 >> 즉 requestMatchers에 있는 url 요청시 인증/ 인가 없이도 접근할 수 있음
//                .anyRequest().authenticated()// 위에서 설정한 url 외의 요청에 대해서 설정 . 별도의 인가는 필요하지 않지만 인증이 접근할 수 있음
//                .and()
//                .formLogin() // 4. 폼 기반 로그인 설정
//                .loginPage("/login") // 로그인 페이지 경로 설정
//                .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로 설정
//                .and()
//                .logout() // 5. 로그아웃 설정
//                .logoutSuccessUrl("/login") // 로그아웃이 완료되었을 때 이동할 경로 설정
//                .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정
//                .and()
//                .csrf().disable() //6. csrf 비활성화
//                .build();
//    }
//
//    // 7. 인증 관리자 관련 설정
//    // 사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법 (ex. LDAP, JDBC 기반 인증 등을 설정할 때 사용)
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userService)// 8. 사용자 정보 서비스 설정 (이 때 설정하는 서비스 클래스는 반드시 UserDetailsService를 상속받은 클래스)
//                .passwordEncoder(bCryptPasswordEncoder) // 비밀번호 암호화위한 인코더
//                .and()
//                .build();
//    }
//
//    // 9. 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
