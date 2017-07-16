package com.userCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userCrud.dao.UserDAO;
import com.userCrud.dto.SearchDTO;
import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;

@Service
public class UserService implements ICrudService<User> {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User getById(long userId) {
		User obj = userDAO.getById(userId);
		return obj;
	}
	
	@Override
	public List<User> getAll(SearchDTO dto){
		return userDAO.getAllWithFilter(dto);
	}
	
	@Override
	public synchronized boolean add(User user){
       if ( user.getName() == null || user.getRole() == null || 
    		   userDAO.userExists(user.getName(), user.getRole())) {
    	   return false;
       } else {
    	   userDAO.add(user);
    	   return true;
       }
	}
	
	@Override
	public User update(User user) {
		return userDAO.update(user);
	}
	
	@Override
	public boolean delete(long userId) {
		return userDAO.delete(userId);
	}
}
