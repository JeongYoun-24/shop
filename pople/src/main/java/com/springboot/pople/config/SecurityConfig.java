package com.springboot.pople.config;


import com.springboot.pople.security.CustomUserDetailsService;
import com.springboot.pople.security.handler.Custom403Handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메서드 실행전에 권한 검사
public class SecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;


    // 로그인과정 생략 : 개발자 직접 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("----- configure ------");

        http.formLogin().loginPage("/users/login") //사용자가 설정한 로그인 페이지
                .defaultSuccessUrl("/main")//로그인 성공시 이동 경로
                .usernameParameter("email")//로그인시 사용될 유저 이름
                .failureUrl("/users/login/error")
                .and()//그리고
                .logout() //로그아웃
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))//로그아웃 경로
                .logoutSuccessUrl("/main"); //로그아웃 성공시 이동 경로

        http.csrf().disable();
        http.formLogin().loginPage("/users/login");

        //인증되지 않은 사용자가 리소스 요청할 경우 에러
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

/*
        //   "/","/board/**" ,"/members/**","/item/**"
        //인가(권한에 대한 접근) 과정 처리
        http.authorizeRequests()
                .mvcMatchers("/bootstrap/**","/js/**","/css/**","/assets/**","/imgs/**"


                ,"/","/board/**" ,"/members/**","/item/**"
                     ).permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest() //인증 과정 요청
                .authenticated(); //리소스 접근 경로 설정 외에는 모두 인증을 요구하도록 설정
*/


        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());// 403 에러 처리

        // 자동 로그인 처리
        http.rememberMe().key("12345678")  // 키값 생성
                .tokenRepository(persistentTokenRepository()) // 토근 생성
                .userDetailsService(customUserDetailsService) // 서큐리티 로그인 정보
                .tokenValiditySeconds(60*60*24*30); // 유효기간 30일



        return http.build();
    }

    // 해시코드로 암호화 처리
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 정적자원(resources -> static)들은 스프링시큐리티 적용에서 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("--0--0-0-0-0-0-0-");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }

    // 접근 권한 거부 예외발생 처리
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){

        return  new Custom403Handler();
    }

    // 자동 로그인 (로그인정보 :UserDatoils외 db유저)
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repo =new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);

        return  repo;
    }









}
