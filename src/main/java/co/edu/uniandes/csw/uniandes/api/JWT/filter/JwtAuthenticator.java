/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.uniandes.api.JWT.filter;

import co.edu.uniandes.csw.uniandes.api.JWT.VerifyToken;
import co.edu.uniandes.csw.uniandes.user.logic.dto.UserDTO;
import java.util.Date;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 *
 * @author Jj.alarcon10
 */
public class JwtAuthenticator  implements Authenticator{

	public AuthenticationInfo authenticate(AuthenticationToken at) throws AuthenticationException {
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
//		VerifyToken ver = new VerifyToken();
//		UserDTO user = VerifyToken.getDataUser(at.toString());
		info.setPrincipals(new SimplePrincipalCollection("user","user"));		
		return info;
	}
	
}
