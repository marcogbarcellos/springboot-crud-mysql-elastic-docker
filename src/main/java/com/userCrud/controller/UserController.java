package com.userCrud.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;
import com.userCrud.service.ICrudService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private ICrudService<User> userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userService.getById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping()
	@ResponseBody
	public ResponseEntity<List<User>> getAllUsers(UserSearchDTO dto) {
		List<User> list = userService.getAll(dto);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder) {
        boolean flag = userService.add(user);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		user.setId(id);
		user = userService.update(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
} 