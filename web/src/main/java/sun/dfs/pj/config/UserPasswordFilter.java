package sun.dfs.pj.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPasswordFilter extends UsernamePasswordAuthenticationFilter {
        SessionRegistry sessionRegistry;

public void setSessionRegistry(SessionRegistry sessionRegistry){
        this.sessionRegistry = sessionRegistry;
        }

//验证登录
@Override
public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("user").trim();
        String password = request.getParameter("psd").trim();
        sessionRegistry.registerNewSession(request.getSession(true).getId(), username);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
        }
        }
