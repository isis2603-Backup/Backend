/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.filters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 *
 * @author Jj.alarcon10
 */
public class FilterOptions extends
		BasicHttpAuthenticationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		String httpMethod = httpRequest.getMethod();
		if ("OPTIONS".equalsIgnoreCase(httpMethod)) {
			return true;
		} else {
			return super.isAccessAllowed(request, response, mappedValue);
		}
	}
}
