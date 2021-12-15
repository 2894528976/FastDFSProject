package sun.dfs.pj.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import sun.dfs.pj.model.RespBean;
import sun.dfs.pj.model.User;


import javax.sql.DataSource;
import java.io.PrintWriter;
@Configuration
@Slf4j
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    String username= "user";
    @Autowired
    DataSource dataSource;
    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    UserPasswordFilter loginFilter() throws Exception {
        UserPasswordFilter loginFilter = new UserPasswordFilter();
        loginFilter.setUsernameParameter(username);
        loginFilter.setPasswordParameter("psd");
        //成功回调
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
            User u = (User) authentication.getPrincipal();
            u.setPassword("");
            RespBean respBean =RespBean.OK("登录成功!",u);
            String s = new ObjectMapper().writeValueAsString(respBean);
            log.info("用户: "+request.getParameter(username)+"登录成功!");
            response.getWriter().write(s);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
        );
        //失败回调
        loginFilter.setAuthenticationFailureHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
            RespBean respBean =RespBean.ERROR("登陆失败!");
            String s = new ObjectMapper().writeValueAsString(respBean);
            writer.write(s);
            log.info("用户: "+request.getParameter(username)+"登录失败!");
                    writer.flush();
                    writer.close();
                }
        );
        /**
         * 创建SessionRegistryImpl时先声明SessionRegistryImpl为全局唯一
         * (添加SessionRegistryImpl为Bean)
         */
        loginFilter.setSessionRegistry(sessionRegistry());
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        //登陆请求
        loginFilter.setFilterProcessesUrl("/login");
        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy =
                new RegisterSessionAuthenticationStrategy(sessionRegistry());
        loginFilter.setSessionAuthenticationStrategy(registerSessionAuthenticationStrategy);
        return loginFilter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class);
        /**
         *
         http
         //限制访问
         .authorizeRequests()
         //                允许所有请求
         .antMatchers("/sun/**").hasRole("admin")
         .antMatchers("/jia/**").hasRole("user")

         //                .antMatchers("/root/**").hasRole("root")
         .anyRequest()
         //登陆后的任意身份都可以访问
         .authenticated()
         .and()
         */
        http
                .authorizeRequests()
                .antMatchers(
                        "/js/**",
                        "/css/**",
                        "/",
                        "/fonts/**",
                        "/login.html",
                         "**/favicon.ico",
                        "/doc.html", "/swagger-ui/**",
                        "/webjars/**",
                        "/needLogin",
                        "/swagger*/**",
                        "/v2/api-docs",
                        "/index.html",
                        "/static/**",
                        "/v3/**"
                )
//                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .cors().disable()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, a) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    log.info("有未登录用户尝试访问其他页面");
                    RespBean respBean =RespBean.NOT_LOGIN("请先登录!");
                    String s = new ObjectMapper().writeValueAsString(respBean);
                    writer.write(s);
                    writer.flush();
                    writer.close();
                })
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    log.info("用户: "+req.getParameter(username)+"退出成功!");
                    RespBean respBean =RespBean.ERROR("退出成功!");
                    String s = new ObjectMapper().writeValueAsString(respBean);
                    writer.write(s);
                    writer.flush();
                    writer.close();

                })
                .and()
                .httpBasic()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();

    }


}
//在登陆的时候注册token 并将密码设置为 JWT生成的token中
//在成功回调中设置token

//class MyBasicAuthen extends BasicAuthenticationFilter{
//
//    public MyBasicAuthen(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    public MyBasicAuthen(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
//        super(authenticationManager, authenticationEntryPoint);
//    }
//
//}
