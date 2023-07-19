package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toDto(EmployeeEntity employeeEntity);

    @Mapping(target = "id", ignore = true)
    EmployeeEntity toEntity(EmployeeDTO employeeDTO);
}
