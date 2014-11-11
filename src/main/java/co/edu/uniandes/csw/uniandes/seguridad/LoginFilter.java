/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.uniandes.seguridad;

import co.edu.uniandes.csw.uniandes.api.JWT.VerifyToken;
import co.edu.uniandes.csw.uniandes.user.logic.dto.UserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 *
 * @author Jj.alarcon10
 */
public class LoginFilter extends AuthenticatingFilter {

    private static final boolean debug = true;

	// The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public LoginFilter() {
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse sr1) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return new JwtToken(httpRequest.getHeader("x_rest_user"));
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse toHttp = WebUtils.toHttp(response);
        try {
            toHttp.sendError(401);
        } catch (IOException ex) {
            Logger.getLogger(LoginFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest sr, ServletResponse sr1) throws Exception {
        return executeLogin(sr, sr1);
    } 


}
