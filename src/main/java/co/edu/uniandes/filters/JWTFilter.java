/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.filters;

/**
 *
 * @author Jj.alarcon10
 */
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class JWTFilter implements ContainerRequestFilter{

public void filter(ContainerRequestContext ctx) throws IOException{

String servicio=ctx.getUriInfo().getPath();
String methodOverride = ctx.getHeaderString("X_REST_USER");
if(servicio.equals("/webresources/Auth/login")){
	

}else if(methodOverride!=null){
		ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build());
}



}

}