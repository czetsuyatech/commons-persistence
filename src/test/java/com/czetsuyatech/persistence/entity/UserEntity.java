package com.czetsuyatech.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_account")
public class UserEntity extends BaseEntity {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "birth_date")
  private LocalDateTime birthDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "orientation")
  private OrientationEnum orientation;

  @Column(name = "favorite_no")
  private Integer favoriteNo;

  @ElementCollection
  @CollectionTable(
      name = "user_hobby",
      joinColumns = @JoinColumn(name = "user_id")
  )
  @Column(name = "hobby")
  private List<String> hobbies;

  @OneToOne(mappedBy = "user")
  private AddressEntity address;
}
