package com.example.educationmanagementsystem.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 登录接口不需要认证
                        .requestMatchers("/api/faculty/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/taro/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/major/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/class/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/course/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/teacher/searchOne").hasAnyAuthority("ROLE_管理员", "ROLE_教师")
                        .requestMatchers("/api/teacher/update/**").hasAnyAuthority("ROLE_管理员", "ROLE_教师")
                        .requestMatchers("/api/teacher/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/student/searchOne").hasAnyAuthority("ROLE_管理员", "ROLE_学生")
                        .requestMatchers("/api/student/update/**").hasAnyAuthority("ROLE_管理员", "ROLE_学生")
                        .requestMatchers("/api/student/delete").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/users/delete/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/users/deleteBatch").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/users/search").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/teaching/search2").hasAnyAuthority("ROLE_管理员", "ROLE_教师")
                        .requestMatchers("/api/teaching/**").hasAuthority("ROLE_管理员")
                        .requestMatchers("/api/teachStu/**").hasAnyAuthority("ROLE_管理员", "ROLE_教师")
                        .requestMatchers("/api/learning/search").hasAnyAuthority("ROLE_管理员", "ROLE_教师", "ROLE_学生")
                        .requestMatchers("/api/learning/**").hasAnyAuthority("ROLE_管理员", "ROLE_教师")
                        .requestMatchers("/api/learnTeacher/**").hasAnyAuthority("ROLE_管理员", "ROLE_学生")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
