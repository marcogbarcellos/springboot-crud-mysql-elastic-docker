package com.userCrud.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@SuppressWarnings("serial")
public class AbstractTimeStampModel implements Serializable { 
	
	@Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
	
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
	
	@PrePersist
    public void onPrePersist() {
        this.createdAt = this.updatedAt = new Date();
    }
      
    @PreUpdate
    public void onPreUpdate() {
    	this.updatedAt = new Date();
    }
}
