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
        //????????????
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
            User u = (User) authentication.getPrincipal();
            u.setPassword("");
            RespBean respBean =RespBean.OK("????????????!",u);
            String s = new ObjectMapper().writeValueAsString(respBean);
            log.info("??????: "+request.getParameter(username)+"????????????!");
            response.getWriter().write(s);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
        );
        //????????????
        loginFilter.setAuthenticationFailureHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
            RespBean respBean =RespBean.ERROR("????????????!");
            String s = new ObjectMapper().writeValueAsString(respBean);
            writer.write(s);
            log.info("??????: "+request.getParameter(username)+"????????????!");
                    writer.flush();
                    writer.close();
                }
        );
        /**
         * ??????SessionRegistryImpl????????????SessionRegistryImpl???????????????
         * (??????SessionRegistryImpl???Bean)
         */
        loginFilter.setSessionRegistry(sessionRegistry());
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        //????????????
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
         //????????????
         .authorizeRequests()
         //                ??????????????????
         .antMatchers("/sun/**").hasRole("admin")
         .antMatchers("/jia/**").hasRole("user")

         //                .antMatchers("/root/**").hasRole("root")
         .anyRequest()
         //???????????????????????????????????????
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
                    log.info("??????????????????????????????????????????");
                    RespBean respBean =RespBean.NOT_LOGIN("????????????!");
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
                    log.info("??????: "+req.getParameter(username)+"????????????!");
                    RespBean respBean =RespBean.ERROR("????????????!");
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
//????????????????????????token ????????????????????? JWT?????????token???
//????????????????????????token

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
