package com.userCrud.service;

import java.util.List;

import com.userCrud.dto.SearchDTO;

public interface ICrudService<T> {
     List<T> getAll(SearchDTO dto);
     T getById(long id);
     boolean add(T obj);
     T update(T obj);
     boolean delete(long id);
}
