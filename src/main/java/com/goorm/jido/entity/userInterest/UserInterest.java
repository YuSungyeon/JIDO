package com.goorm.jido.entity.userInterest;

import com.goorm.jido.entity.Category;
import com.goorm.jido.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_interest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Builder
  public UserInterest(User user, Category category) {
    this.user = user;
    this.category = category;
  }
}
