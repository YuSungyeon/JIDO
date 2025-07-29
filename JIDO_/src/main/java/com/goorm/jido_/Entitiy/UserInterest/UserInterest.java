package com.goorm.jido_.Entitiy.UserInterest;

import com.goorm.jido_.Entitiy.Category;
import com.goorm.jido_.Entitiy.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_interest")
@IdClass(UserInterestId.class)
public class UserInterest {
  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
