package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Named("toOwnerId")
    static long toOwnerId(CustomerEntity entity) {
        return entity.getId();
    }

    @Mapping(source = "owner", target = "ownerId", qualifiedByName = "toOwnerId")
    PetDTO toDto(PetEntity petEntity);

    @Mapping(target = "id", ignore = true)
    PetEntity toEntity(PetDTO petDTO);
}
