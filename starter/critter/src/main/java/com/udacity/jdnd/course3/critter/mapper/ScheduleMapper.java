package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.common.BaseEntity;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Named("toPetIds")
    static List<Long> toPetIds(List<PetEntity> entities) {
        return entities == null ? new ArrayList<>() : entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @Named("toEmployeeIds")
    static List<Long> toEmployeeIds(List<EmployeeEntity> entities) {
        return entities == null ? new ArrayList<>() : entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @Mapping(source = "pets", target = "petIds", qualifiedByName = "toPetIds")
    @Mapping(source = "employees", target = "employeeIds", qualifiedByName = "toEmployeeIds")
    ScheduleDTO toDto(ScheduleEntity scheduleEntity);

    @Mapping(target = "id", ignore = true)
    ScheduleEntity toEntity(ScheduleDTO scheduleDTO);
}
