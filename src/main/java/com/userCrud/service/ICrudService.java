package com.userCrud.service;

import java.util.List;

import com.userCrud.dto.SearchDTO;
import com.userCrud.model.User;

public interface ICrudService<T> {
     List<T> getAll(SearchDTO dto);
     User getById(long id);
     boolean add(T obj);
     T update(T obj);
     void delete(long id);
}
