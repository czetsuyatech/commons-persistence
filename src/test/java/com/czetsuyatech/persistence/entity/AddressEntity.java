package com.czetsuyatech.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_address")
public class AddressEntity extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(name = "city")
  private String city;

  @Column(name = "country")
  private String country;
}
