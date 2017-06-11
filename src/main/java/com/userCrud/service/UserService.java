package com.userCrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userCrud.dao.UserDAO;
import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User getUserById(long userId) {
		User obj = userDAO.getUserById(userId);
		return obj;
	}
	
	@Override
	public List<User> getAllUsers(UserSearchDTO dto){
		return userDAO.getAllUsers(dto);
	}
	
	@Override
	public synchronized boolean addUser(User user){
       if ( user.getName() == null || user.getRole() == null || 
    		   userDAO.userExists(user.getName(), user.getRole())) {
    	   return false;
       } else {
    	   userDAO.addUser(user);
    	   return true;
       }
	}
	
	@Override
	public User updateUser(User user) {
		return userDAO.updateUser(user);
	}
	
	@Override
	public void deleteUser(long userId) {
		userDAO.deleteUser(userId);
	}
}
