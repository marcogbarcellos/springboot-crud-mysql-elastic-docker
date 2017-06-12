package com.userCrud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
@SuppressWarnings("serial")
public class AbstractTimeStampModel implements Serializable { 
	
	@Column(name = "created_at")
	@ApiModelProperty(hidden = true)
    private Date createdAt;

    @Column(name = "updated_at")
    @ApiModelProperty(hidden = true)
    private Date updatedAt;
    
    @Column(name = "activated")
    @ApiModelProperty(hidden = true)
    private Boolean activated;
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Boolean getActivated() {
		return activated;
	}
	
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	@PrePersist
    public void onPrePersist() {
        this.createdAt = this.updatedAt = new Date();
    }
      
    @PreUpdate
    public void onPreUpdate() {
    	this.updatedAt = new Date();
    }
}
