package com.userCrud.service;

import java.util.List;

import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;

public interface IUserService {
     List<User> getAllUsers(UserSearchDTO dto);
     User getUserById(long articleId);
     boolean addUser(User article);
     User updateUser(User article);
     void deleteUser(long articleId);
}
