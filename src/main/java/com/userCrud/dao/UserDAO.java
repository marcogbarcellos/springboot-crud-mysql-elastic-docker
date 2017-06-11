package com.userCrud.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;

@Transactional
@Repository
public class UserDAO implements IUserDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public User getUserById(long userId) {
		return entityManager.find(User.class, userId);
	}

	@Override
	public void addUser(User user) {
		entityManager.persist(user);
	}

	@Override
	public User updateUser(User user) {
		User userToUpdate = getUserById(user.getId());
		if ( user.getName() != null ) {
			userToUpdate.setName(user.getName());
		}
		if ( user.getRole() != null ) {
			userToUpdate.setRole(user.getRole());
		}
		return (User) entityManager.merge(userToUpdate);
	}

	@Override
	public void deleteUser(long userId) {
		entityManager.remove(getUserById(userId));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers(UserSearchDTO dto) {
		String hql = " FROM User as u WHERE 1=1 ";
		
		//validating and adding parameters(if they exist)
		if ( dto.getId() != null && dto.getId().length > 0 ) {
			hql += " AND u.id in (:ids) ";
		}
		if ( dto.getName() != null && dto.getName().length > 0 ) {
			hql += " AND u.name in (:names) ";
		}
		if ( dto.getRole() != null && dto.getRole().length > 0 ) {
			hql += " AND u.role in (:roles) ";
		}
		
		hql += " ORDER BY u.updatedAt DESC";
		Query query = entityManager.createQuery(hql);
		//setting query arguments after having it defined
		if ( dto.getId() != null && dto.getId().length > 0 ) {
			hql += " AND u.id in (:ids) ";
			query.setParameter("ids", Arrays.asList(dto.getId()));
		}
		if ( dto.getName() != null && dto.getName().length > 0 ) {
			query.setParameter("names", Arrays.asList(dto.getName()));
		}
		if ( dto.getRole() != null && dto.getRole().length > 0 ) {
			query.setParameter("roles", Arrays.asList(dto.getRole()));
		}
		
		return (List<User>) query.getResultList();
	}

	@Override
	public boolean userExists(String title, String role) {
		String hql = "FROM User as u WHERE u.name = ? and u.role= ?";
		int count = entityManager.createQuery(hql).setParameter(1, title)
		              .setParameter(2, role).getResultList().size();
		return count > 0 ? true : false;
	}

}
