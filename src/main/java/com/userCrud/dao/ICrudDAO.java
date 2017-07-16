package com.userCrud.dao;

import java.util.List;

import com.userCrud.dto.SearchDTO;
import com.userCrud.model.IModel;

public interface ICrudDAO<T extends IModel> {
	
    T getById(long id);
    void add(T obj);
    T update(T obj);
    boolean delete(long id);
    List<T> getAll();
    List<T> getAllWithFilter(SearchDTO dto);
}
