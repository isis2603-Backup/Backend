/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.uniandes.sport.logic.dto;

/**
 *
 * @author Jj.alarcon10
 */
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class _SportPageDTO {

    private Long totalRecords;

    private List<SportDTO> records;

    public Long getTotalRecords() {
	return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
	this.totalRecords = totalRecords;
    }

    public List<SportDTO> getRecords() {
	return records;
    }

    public void setRecords(List<SportDTO> records) {
	this.records = records;
    }
}