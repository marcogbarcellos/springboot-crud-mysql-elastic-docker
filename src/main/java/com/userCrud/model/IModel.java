package com.userCrud.model;

import java.io.Serializable;

public interface IModel extends Serializable {
  public Long getId();
  public void setId(Long id);
  public Boolean getActivated();
  public void setActivated(Boolean activated);
}
