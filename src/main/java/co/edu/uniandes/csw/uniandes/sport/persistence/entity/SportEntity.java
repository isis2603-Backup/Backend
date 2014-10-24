/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.uniandes.sport.persistence.entity;

/**
 *
 * @author Jj.alarcon10
 */
import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author Jj.alarcon10
 */
@Entity
@XmlRootElement
@Multitenant
@TenantDiscriminatorColumn(name="Tenant-id")
public class SportEntity implements Serializable{
     private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    
    private Long id;
    
    @Transient
    private Long componentId;
    
    private String name;
    
    private Integer minAge;
    
    private Integer maxAge;
    
    
    //
    
    public SportEntity(){
        
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComponentId() {
        return componentId;
    }
    
  
    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
    
}
