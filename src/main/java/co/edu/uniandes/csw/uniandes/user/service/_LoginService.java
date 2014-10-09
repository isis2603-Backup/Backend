/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.uniandes.user.service;

import co.edu.uniandes.csw.uniandes.user.persistence.entity.UserEntity;
import co.edu.uniandes.csw.uniandes.user.logic.dto.Login;
import co.edu.uniandes.csw.uniandes.user.logic.dto.UserDTO;
import co.edu.uniandes.csw.uniandes.persistence.PersistenceManager;
import co.edu.uniandes.csw.uniandes.user.persistence.converter.UserConverter;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Jj.alarcon10
 */
public abstract class _LoginService {
    
    @PersistenceContext(unitName="SportPU")
 
	protected EntityManager entityManager;


        @PostConstruct
        public void init() {
            try {
                entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         
    
    
    
    @POST
        @Path("/login")
        public UserDTO login(Login login){
            System.out.println("Entro al login");
            boolean rta = false;
            UserEntity us=new UserEntity();
            try{
                us =(UserEntity)entityManager.createQuery("select u from UserEntity u WHERE u.email='"+login.getUserName()+"'").getSingleResult();
                System.out.println(us.getUserName());
                if(us!=null && us.getEmail().equalsIgnoreCase(login.getUserName())&& us.getPassword().equalsIgnoreCase(login.getPassword())){
                    rta=true;
                    UserDTO user = new UserDTO();
                    user = UserConverter.entity2PersistenceDTO(us);
                    user.setPassword("");
                    return user;
                }
            }
            catch(Exception e){
                e.printStackTrace();
                e.getMessage();
            }
            finally{
                entityManager.clear();
                entityManager.close();
            }
            return null;
    }
    
}
