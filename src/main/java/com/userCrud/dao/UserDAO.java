package com.userCrud.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.userCrud.dto.SearchDTO;
import com.userCrud.dto.UserSearchDTO;
import com.userCrud.model.User;

@Transactional
@Repository
public class UserDAO implements ICrudDAO<User>{
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public User getById(long userId) {
		User user = entityManager.find(User.class, userId); 
		return ( user != null && user.getActivated() ) ? user : null;
	}

	@Override
	public void add(User user) {
		user.setActivated(true);
		entityManager.persist(user);
	}

	@Override
	public User update(User user) {
		User userToUpdate = getById(user.getId());
		if ( userToUpdate != null && user.getName() != null ) {
			userToUpdate.setName(user.getName());
		}
		if ( userToUpdate != null && user.getRole() != null ) {
			userToUpdate.setRole(user.getRole());
		}
		return (User) entityManager.merge(userToUpdate);
	}

	@Override
	public void delete(long userId) {
		User userToRemove = getById(userId);
		if ( userToRemove != null ) {
			userToRemove.setActivated(false);
			entityManager.merge(userToRemove);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll(SearchDTO dto) {
		UserSearchDTO userDto = (UserSearchDTO) dto;
		
		String hql = " FROM User as u WHERE u.activated=true ";
		
		//validating and adding parameters(if they exist)
		if ( userDto.getId() != null && userDto.getId().length > 0 ) {
			hql += " AND u.id in (:ids) ";
		}
		if ( userDto.getName() != null && userDto.getName().length > 0 ) {
			hql += " AND u.name in (:names) ";
		}
		if ( userDto.getRole() != null && userDto.getRole().length > 0 ) {
			hql += " AND u.role in (:roles) ";
		}
		
		hql += " ORDER BY u.updatedAt DESC";
		Query query = entityManager.createQuery(hql);
		//setting query arguments after having it defined
		if ( userDto.getId() != null && userDto.getId().length > 0 ) {
			hql += " AND u.id in (:ids) ";
			query.setParameter("ids", Arrays.asList(userDto.getId()));
		}
		if ( userDto.getName() != null && userDto.getName().length > 0 ) {
			query.setParameter("names", Arrays.asList(userDto.getName()));
		}
		if ( userDto.getRole() != null && userDto.getRole().length > 0 ) {
			query.setParameter("roles", Arrays.asList(userDto.getRole()));
		}
		
		return (List<User>) query.getResultList();
	}

	public boolean userExists(String title, String role) {
		String hql = "FROM User as u WHERE u.name = ? and u.role= ?";
		int count = entityManager.createQuery(hql).setParameter(1, title)
		              .setParameter(2, role).getResultList().size();
		return count > 0 ? true : false;
	}

}
