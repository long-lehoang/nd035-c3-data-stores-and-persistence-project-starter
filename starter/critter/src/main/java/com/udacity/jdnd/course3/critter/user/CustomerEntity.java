package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.common.BaseUserEntity;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
@Builder
public class CustomerEntity extends BaseUserEntity {
    private String phoneNumber;

    @OneToMany(mappedBy = "owner")
    private List<PetEntity> pets;
}
