package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.common.BaseEntity;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Named("toPetIds")
    static List<Long> toPetIds(List<PetEntity> entities) {
        return entities == null ? new ArrayList<>() : entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @Mapping(source = "pets", target = "petIds", qualifiedByName = "toPetIds")
    CustomerDTO toDto(CustomerEntity customerEntity);
}
