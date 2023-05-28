package com.examsystem.config;

import com.examsystem.common.SystemCode;
import com.examsystem.config.security.LoginAuthenticationEntryPoint;
import com.examsystem.config.security.RestAccessDeniedHandler;
import com.examsystem.entity.enums.RoleEnum;
import com.examsystem.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 权限控制
 * @DATE: 2023/5/23 15:31
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;

    /**
     * 创建BCryptPasswordEncoder注入容器,用户密码加密存储
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        //权限控制
//        http.authorizeRequests()
//                .antMatchers(
//                        "/api/admin/**"
//                )
//                .hasAnyAuthority(
//                        RoleEnum.ADMIN.getName()
//                )
//                .antMatchers(
//                        "/api/student/**"
//                )
//                //如果用户具备给定权限中的某一个的话，就允许访问
//                .hasAnyAuthority(
//                        RoleEnum.STUDENT.getName()
//                )
//
//                //代表其他请求需要认证
//                .anyRequest().authenticated()
//                //权限异常处理
//                //.and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
//
//                .and().csrf().disable();

        http    //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口 允许匿名访问
                .antMatchers("/api/user/login").anonymous()
                //.antMatchers("/api/admin/**").hasAnyAuthority(RoleEnum.ADMIN.getName())
                //除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //把token校验过滤添加到过滤器中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(loginAuthenticationEntryPoint);
                //.and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置跨源访问
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setMaxAge(3600L);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
