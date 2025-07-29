package com.goorm.jido_.Entitiy.UserInterest;

import java.io.Serializable;
import java.util.Objects;

// UserInterest 복합키 클래스
public class UserInterestId implements Serializable {
  private Long userId;
  private String categoryId;

  public UserInterestId(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserInterestId)) return false;
    UserInterestId that = (UserInterestId) o;
    return Objects.equals(userId, that.userId) &&
            Objects.equals(categoryId, that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, categoryId);
  }
}
