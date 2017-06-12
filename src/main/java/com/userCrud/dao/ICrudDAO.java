package com.userCrud.dao;

import java.util.List;

import com.userCrud.dto.SearchDTO;

public interface ICrudDAO<T> {
	
    T getById(long id);
    void add(T obj);
    T update(T obj);
    void delete(long id);
    List<T> getAll(SearchDTO dto);
}
