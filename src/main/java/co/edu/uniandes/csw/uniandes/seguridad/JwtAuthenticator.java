/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.uniandes.seguridad;

import co.edu.uniandes.csw.uniandes.api.JWT.VerifyToken;
import co.edu.uniandes.csw.uniandes.user.logic.dto.UserDTO;
import java.util.Date;
import org.apache.shiro.authc.AccountException;
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
public class JwtAuthenticator implements Authenticator {

    public AuthenticationInfo authenticate(AuthenticationToken at) throws AuthenticationException {
        JwtToken authToken = (JwtToken) at;
        if (authToken.getToken() != null) { 
            //Descifrar token y establecer info de usuario
            if (validarToken(authToken.getToken())) {
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
                info.setPrincipals(new SimplePrincipalCollection(new Usuario("usuario1", "1"), "usuario1"));
                return info;
            }
        }
        throw new AccountException("Token invalido.");
    }

    public boolean validarToken(String token) {
        return token.equals("123");
    }

}
