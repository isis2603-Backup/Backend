/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.uniandes.seguridad;

/**
 *
 * @author cadmilo
 */
public class Usuario {
    
    private String nombre;
    private String tenant;

    public Usuario(String nombre, String tenant) {
        this.nombre = nombre;
        this.tenant = tenant;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
    
    
    
}
