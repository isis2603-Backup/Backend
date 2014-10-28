/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.uniandes.sport.service;

import co.edu.uniandes.csw.uniandes.api.JWT.JsonWebToken;
import co.edu.uniandes.csw.uniandes.api.JWT.JwtHashAlgorithm;
import co.edu.uniandes.csw.uniandes.persistence.PersistenceManager;
import co.edu.uniandes.csw.uniandes.user.logic.dto.Login;
import co.edu.uniandes.csw.uniandes.user.logic.dto.UserDTO;
import co.edu.uniandes.csw.uniandes.user.persistence.converter.UserConverter;
import co.edu.uniandes.csw.uniandes.user.persistence.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.simple.JSONObject;

/**
 *
 * @author Jj.alarcon10
 */
public abstract class _LoginService {

	@PersistenceContext(unitName = "SportPU")

	protected EntityManager entityManager;

	@PostConstruct
	public void init() {
		try {

			entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
//			Map<String, Object> emProperties = new HashMap<String, Object>();
//			emProperties.put("eclipselink.tenant-id", "1");//Asigna un valor al multitenant
//			entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager(emProperties);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Path("/login")
	@OPTIONS
	public Response cors(@javax.ws.rs.core.Context HttpHeaders requestHeaders) {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Allow-Headers", "AUTHORIZATION, content-type, accept").build();
	}

	/**
	 * Servicio que ofrece la autenticacion de usuarios
	 *
	 *
	 * @param login credenciales de usuario return JWT TOKEN
	 */
	@POST
	@Path("/login")
	public Response login(Login login) {
		System.out.println("Entro al login");
		boolean rta = false;

		try {
			UserEntity us = new UserEntity();
			us = (UserEntity) entityManager.createQuery("select u from UserEntity u WHERE u.userName='" + login.getUserName() + "'").getSingleResult();
			System.out.println(us.getUserName());
			if (us != null && us.getEmail().equalsIgnoreCase(login.getUserName()) && us.getPassword().equalsIgnoreCase(login.getPassword())) {
				rta = true;
				UserDTO user = new UserDTO();
				user = UserConverter.entity2PersistenceDTO(us);
				user.setPassword("");
				System.out.println("rta: " + user);
				String g = JsonWebToken.encode(user, "Ejemplo", JwtHashAlgorithm.HS512);
				String json = new Gson().toJson(g);
				return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(json).build();

			} else {

				JSONObject obj = new JSONObject();
				obj.put("Message", "Problem matching service key, username and password");
				return Response.status(401).header("Access-Control-Allow-Origin", "*").entity(obj).build();

			}

		} catch (Exception e) {

			return Response.status(401).header("Access-Control-Allow-Origin", "*").build();
		} finally {
			entityManager.clear();
			entityManager.close();
		}

	}

	@Path("/login_test")
	@OPTIONS
	public Response cors1(@javax.ws.rs.core.Context HttpHeaders requestHeaders) {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Allow-Headers", "AUTHORIZATION, content-type, accept").build();
	}

	@POST
	@Path("/login_test")
	public Response login2(Login login) {
		System.out.println("Entro al login");
		boolean rta = false;

		try {
			UsernamePasswordToken token
					= new UsernamePasswordToken(login.getUserName(), login.getPassword());
			//”Remember Me” built-in, just do this:
			token.setRememberMe(true);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);
			System.out.println("Entro al login:::::::");
			UserEntity us = new UserEntity();
			us = (UserEntity) entityManager.createQuery("select u from UserEntity u WHERE u.userName='" + login.getUserName() + "'").getSingleResult();
			System.out.println(us.getUserName());
			System.out.println(us.getTenant());
			UserDTO user = new UserDTO();
			user = UserConverter.entity2PersistenceDTO(us);
			System.out.println("rta: " + user);
			String g = JsonWebToken.encode(user, "Ejemplo", JwtHashAlgorithm.HS512);
			String json = new Gson().toJson(g);
			return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(json).build();

		} catch (UnknownAccountException uae) {
			return Response.status(401).header("Access-Control-Allow-Origin", "*").entity("Incorrect Password").build();
		} catch (IncorrectCredentialsException ice) {
			return Response.status(401).header("Access-Control-Allow-Origin", "*").entity("Incorrect Password").build();
		} catch (LockedAccountException lae) {
			return Response.status(401).header("Access-Control-Allow-Origin", "*").entity("Incorrect Password").build();
		} catch (ExcessiveAttemptsException eae) {
			return Response.status(401).header("Access-Control-Allow-Origin", "*").entity("Incorrect Password").build();

		} catch (AuthenticationException ae) {
			return Response.status(401).header("Access-Control-Allow-Origin", "*").build();
		} finally {
			entityManager.clear();
			entityManager.close();
		}

	}

	/**
	 * Servicio que ofrece el html con el nombre de usuario y el link de cierre
	 * de sesion
	 *
	 * @param req Contexto del usuario que llama al servicio de donde se
	 * obtienen los datos de sesion
	 */
	@GET
	@Path("/session")
	public String getLogedUser(@Context HttpServletRequest req) {

		return "<b>" + req.getRemoteUser() + "</b></br><a href=\"webresources/auth/\">Log Out</a>";
	}

}
