/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.uniandes.api.JWT;

import co.edu.uniandes.csw.uniandes.user.logic.dto.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Jj.alarcon10
 */
public class VerifyToken {

	public static String getDataToken(String token) {

		try {
			String userToken = JsonWebToken.decode(token, "Ejemplo", true);
			Gson gson = new GsonBuilder().serializeNulls().create();
			UserDTO res = gson.fromJson(userToken, UserDTO.class);
			String tenant = res.getTenant();
			return tenant;
		} catch (Throwable t) {
			t.printStackTrace();
			return "error getData";
		}
	}
	
	public static UserDTO getDataUser(String token) {

		try {
			String userToken = JsonWebToken.decode(token, "Ejemplo", true);
			Gson gson = new GsonBuilder().serializeNulls().create();
			UserDTO res = gson.fromJson(userToken, UserDTO.class);
			
			return res;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

}
