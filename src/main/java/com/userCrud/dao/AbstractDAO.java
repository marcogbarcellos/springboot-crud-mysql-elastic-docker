package com.userCrud.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.userCrud.model.IModel;

public abstract class AbstractDAO<T extends IModel> implements ICrudDAO<T> {
  
  @PersistenceContext 
  protected EntityManager entityManager;
  
  protected Class<T> classType; 
  
  @SuppressWarnings("unchecked")
  public AbstractDAO() {
    classType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public T getById(long id) {
    T obj = entityManager.find(classType, id);
    return obj != null ? obj: null;
  }
  
  @Override
  public void add(T obj) {
    entityManager.persist(obj);
  }
  
  @Override
  public T update(T obj) {
    if ( getById(obj.getId()) != null ) {
      return (T) entityManager.merge(obj);
    }
    return null;
  }
  
  @Override
  public boolean delete(long id) {
    T objToRemov = getById(id);
    if ( objToRemov != null ) {
      objToRemov.setActivated(false);
      entityManager.merge(objToRemov);
      return true;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> getAll() {
    return entityManager.createQuery("Select t from " + classType.getSimpleName() + " t").getResultList();
  }

}
