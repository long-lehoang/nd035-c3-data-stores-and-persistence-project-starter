package com.udacity.jdnd.course3.critter.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserEntity extends BaseEntity {
    private String name;
}
