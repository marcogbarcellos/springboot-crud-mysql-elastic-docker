package com.userCrud.dao;

import java.util.List;

import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;

public interface IUserDAO {
	
    User getUserById(long userId);
    void addUser(User user);
    User updateUser(User user);
    void deleteUser(long userId);
    List<User> getAllUsers(UserSearchDTO dto);
    boolean userExists(String title, String role);
}
