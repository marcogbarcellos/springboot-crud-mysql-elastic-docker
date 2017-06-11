package com.userCrud.dto;

/**
 * This Object is used to transport/validate information from the http requests
 * to ensure that only valid information can be persisted.
 */
public final class UserSearchDTO {

    private Long[] id;
    
    private String[] name;
        
    private String[] role;
    
	public Long[] getId() {
		return id;
	}

	public void setId(Long[] id) {
		this.id = id;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}
	
    @Override
    public String toString() {
        return "User DTO{" +
                "id=" + id +
                ", name=" + name +
                ", role=" + role +
                '}';
    }
    
}